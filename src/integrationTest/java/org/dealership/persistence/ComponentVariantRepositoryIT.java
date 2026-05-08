package org.dealership.persistence;

import org.dealership.AbstractIntegrationTest;
import org.dealership.domain.model.enums.ComponentType;
import org.dealership.infrastructure.persistence.jpa.entity.ComponentVariantJpaEntity;
import org.dealership.infrastructure.persistence.jpa.repository.ComponentVariantJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class ComponentVariantRepositoryIT extends AbstractIntegrationTest {

    private static final UUID MODEL_320I_ID = UUID.fromString("00000000-0000-0000-0000-000000000101");
    private static final UUID MODEL_M340I_ID = UUID.fromString("00000000-0000-0000-0000-000000000103");
    private static final UUID WHEELS_17_STD_ID = UUID.fromString("00000000-0000-0000-0000-000000000201");
    private static final UUID WHEELS_19_MSPORT_ID = UUID.fromString("00000000-0000-0000-0000-000000000205");
    private static final UUID WHEELS_18_AERO_ID = UUID.fromString("00000000-0000-0000-0000-000000000206");
    private static final UUID INTERIOR_PERFORMANCE_ID = UUID.fromString("00000000-0000-0000-0000-000000000210");

    @Autowired
    private ComponentVariantJpaRepository componentVariantRepository;

    @Test
    void findByIdAndRemovedFalse_existingVariant_returnsVariantWithCompatibleModels() {
        Optional<ComponentVariantJpaEntity> result = componentVariantRepository.findByIdAndRemovedFalse(WHEELS_19_MSPORT_ID);

        assertThat(result).isPresent();
        ComponentVariantJpaEntity variant = result.get();
        assertThat(variant.getName()).isEqualTo("19 M-Sport");
        assertThat(variant.getComponentType()).isEqualTo(ComponentType.WHEELS);
        assertThat(variant.getCompatibleModels()).hasSize(3);
    }

    @Test
    void findByIdAndRemovedFalse_nonExistingId_returnsEmpty() {
        Optional<ComponentVariantJpaEntity> result = componentVariantRepository.findByIdAndRemovedFalse(UUID.randomUUID());

        assertThat(result).isEmpty();
    }

    @Test
    void findAllByRemovedFalse_returnsAllSeedVariants() {
        List<ComponentVariantJpaEntity> variants = componentVariantRepository.findAllByRemovedFalse();

        assertThat(variants).hasSize(10);
    }

    @Test
    void findAllByIdInAndRemovedFalse_returnsRequestedVariants() {
        List<ComponentVariantJpaEntity> variants = componentVariantRepository.findAllByIdInAndRemovedFalse(
                Set.of(WHEELS_17_STD_ID, WHEELS_19_MSPORT_ID)
        );

        assertThat(variants).hasSize(2);
        assertThat(variants).extracting(ComponentVariantJpaEntity::getId)
                .containsExactlyInAnyOrder(WHEELS_17_STD_ID, WHEELS_19_MSPORT_ID);
    }

    @Test
    void findAllCompatibleWithModel_model320i_returnsCompatibleVariants() {
        List<ComponentVariantJpaEntity> variants = componentVariantRepository.findAllCompatibleWithModel(MODEL_320I_ID);

        assertThat(variants).isNotEmpty();
        assertThat(variants).extracting(ComponentVariantJpaEntity::getId).contains(WHEELS_17_STD_ID);
        assertThat(variants).extracting(ComponentVariantJpaEntity::getId).doesNotContain(INTERIOR_PERFORMANCE_ID);
    }

    @Test
    void findAllCompatibleWithModel_modelM340i_excludesIncompatibleVariants() {
        List<ComponentVariantJpaEntity> variants = componentVariantRepository.findAllCompatibleWithModel(MODEL_M340I_ID);

        assertThat(variants).isNotEmpty();
        assertThat(variants).extracting(ComponentVariantJpaEntity::getId).doesNotContain(WHEELS_17_STD_ID);
        assertThat(variants).extracting(ComponentVariantJpaEntity::getId).contains(WHEELS_19_MSPORT_ID);
    }

    @Test
    void findAllCompatibleWithModel_unknownModel_returnsEmpty() {
        List<ComponentVariantJpaEntity> variants = componentVariantRepository.findAllCompatibleWithModel(UUID.randomUUID());

        assertThat(variants).isEmpty();
    }

    @Test
    void findAllByComponentTypeAndCompatibleModel_wheelsAnd320i_returnsOnlyWheelVariants() {
        List<ComponentVariantJpaEntity> variants = componentVariantRepository.findAllByComponentTypeAndCompatibleModel(
                ComponentType.WHEELS, MODEL_320I_ID
        );

        assertThat(variants).isNotEmpty();
        assertThat(variants).allMatch(v -> v.getComponentType() == ComponentType.WHEELS);
        assertThat(variants).extracting(ComponentVariantJpaEntity::getId)
                .contains(WHEELS_17_STD_ID, WHEELS_19_MSPORT_ID, WHEELS_18_AERO_ID);
    }

    @Test
    void findAllByComponentTypeAndCompatibleModel_unknownModel_returnsEmpty() {
        List<ComponentVariantJpaEntity> variants = componentVariantRepository.findAllByComponentTypeAndCompatibleModel(
                ComponentType.WHEELS, UUID.randomUUID()
        );

        assertThat(variants).isEmpty();
    }

    @Test
    void markRemoved_variantExcludedFromActiveFinders() {
        ComponentVariantJpaEntity variant = componentVariantRepository.findByIdAndRemovedFalse(WHEELS_19_MSPORT_ID).orElseThrow();
        variant.markRemoved();
        componentVariantRepository.saveAndFlush(variant);

        Optional<ComponentVariantJpaEntity> byId = componentVariantRepository.findByIdAndRemovedFalse(WHEELS_19_MSPORT_ID);

        assertThat(byId).isEmpty();
    }
}
