package org.dealership.persistence;

import org.dealership.AbstractIntegrationTest;
import org.dealership.infrastructure.persistence.jpa.entity.SparePartCompatibilityJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.SparePartJpaEntity;
import org.dealership.infrastructure.persistence.jpa.repository.SparePartJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class SparePartRepositoryIT extends AbstractIntegrationTest {

    private static final UUID SPARE_PART_ID = UUID.fromString("00000000-0000-0000-0000-000000000551");
    private static final UUID MODEL_320I_ID = UUID.fromString("00000000-0000-0000-0000-000000000101");
    private static final UUID MODEL_330I_ID = UUID.fromString("00000000-0000-0000-0000-000000000102");

    @Autowired
    private SparePartJpaRepository sparePartRepository;

    @Test
    void findByIdAndRemovedFalse_existingPart_returnsPartWithCompatibleModels() {
        Optional<SparePartJpaEntity> result = sparePartRepository.findByIdAndRemovedFalse(SPARE_PART_ID);

        assertThat(result).isPresent();
        SparePartJpaEntity part = result.get();
        assertThat(part.getName()).isEqualTo("BMW Brake Pads Set");
        assertThat(part.getPrice()).isEqualByComparingTo(new BigDecimal("28000.00"));
        assertThat(part.getCompatibleModels()).hasSize(2);
        Set<UUID> modelIds = part.getCompatibleModels().stream()
                .map(c -> c.getCarModel().getId())
                .collect(java.util.stream.Collectors.toSet());
        assertThat(modelIds).containsExactlyInAnyOrder(MODEL_320I_ID, MODEL_330I_ID);
    }

    @Test
    void findByIdAndRemovedFalse_nonExistingId_returnsEmpty() {
        Optional<SparePartJpaEntity> result = sparePartRepository.findByIdAndRemovedFalse(UUID.randomUUID());

        assertThat(result).isEmpty();
    }

    @Test
    void findAllByRemovedFalse_returnsAllSeedParts() {
        List<SparePartJpaEntity> parts = sparePartRepository.findAllByRemovedFalse();

        assertThat(parts).hasSize(1);
        assertThat(parts.get(0).getId()).isEqualTo(SPARE_PART_ID);
    }

    @Test
    void save_persistsNewPart() {
        UUID newId = UUID.randomUUID();
        SparePartJpaEntity newPart = new SparePartJpaEntity(newId, "Brake Disc", new BigDecimal("15000.00"));

        sparePartRepository.saveAndFlush(newPart);

        Optional<SparePartJpaEntity> found = sparePartRepository.findByIdAndRemovedFalse(newId);
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Brake Disc");
    }

    @Test
    void markRemoved_partExcludedFromActiveFinders() {
        SparePartJpaEntity part = sparePartRepository.findByIdAndRemovedFalse(SPARE_PART_ID).orElseThrow();
        part.markRemoved();
        sparePartRepository.saveAndFlush(part);

        Optional<SparePartJpaEntity> byId = sparePartRepository.findByIdAndRemovedFalse(SPARE_PART_ID);
        List<SparePartJpaEntity> all = sparePartRepository.findAllByRemovedFalse();

        assertThat(byId).isEmpty();
        assertThat(all).isEmpty();
    }

    @Test
    void compatibilityRelations_loadEagerly() {
        SparePartJpaEntity part = sparePartRepository.findByIdAndRemovedFalse(SPARE_PART_ID).orElseThrow();

        for (SparePartCompatibilityJpaEntity compat : part.getCompatibleModels()) {
            assertThat(compat.getCarModel()).isNotNull();
            assertThat(compat.getCarModel().getModelName()).isNotBlank();
        }
    }
}
