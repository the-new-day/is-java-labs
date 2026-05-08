package org.dealership.persistence;

import org.dealership.AbstractIntegrationTest;
import org.dealership.infrastructure.persistence.jpa.entity.CarModelJpaEntity;
import org.dealership.infrastructure.persistence.jpa.repository.CarModelJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class CarModelRepositoryIT extends AbstractIntegrationTest {

    private static final UUID BRAND_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID MODEL_320I_ID = UUID.fromString("00000000-0000-0000-0000-000000000101");
    private static final UUID MODEL_330I_ID = UUID.fromString("00000000-0000-0000-0000-000000000102");
    private static final UUID MODEL_M340I_ID = UUID.fromString("00000000-0000-0000-0000-000000000103");

    @Autowired
    private CarModelJpaRepository carModelRepository;

    @Test
    void findByIdAndRemovedFalse_existingModel_returnsModelWithBrand() {
        Optional<CarModelJpaEntity> result = carModelRepository.findByIdAndRemovedFalse(MODEL_320I_ID);

        assertThat(result).isPresent();
        CarModelJpaEntity model = result.get();
        assertThat(model.getModelName()).isEqualTo("320i");
        assertThat(model.getBrand()).isNotNull();
        assertThat(model.getBrand().getName()).isEqualTo("BMW");
    }

    @Test
    void findByIdAndRemovedFalse_nonExistingId_returnsEmpty() {
        Optional<CarModelJpaEntity> result = carModelRepository.findByIdAndRemovedFalse(UUID.randomUUID());

        assertThat(result).isEmpty();
    }

    @Test
    void findAllByRemovedFalse_returnsAllSeedModels() {
        List<CarModelJpaEntity> models = carModelRepository.findAllByRemovedFalse();

        assertThat(models).hasSize(3);
        assertThat(models).extracting(CarModelJpaEntity::getId)
                .containsExactlyInAnyOrder(MODEL_320I_ID, MODEL_330I_ID, MODEL_M340I_ID);
    }

    @Test
    void findAllByBrandIdAndRemovedFalse_existingBrand_returnsAllBrandModels() {
        List<CarModelJpaEntity> models = carModelRepository.findAllByBrandIdAndRemovedFalse(BRAND_ID);

        assertThat(models).hasSize(3);
        assertThat(models).allMatch(m -> m.getBrand().getId().equals(BRAND_ID));
    }

    @Test
    void findAllByBrandIdAndRemovedFalse_nonExistingBrand_returnsEmpty() {
        List<CarModelJpaEntity> models = carModelRepository.findAllByBrandIdAndRemovedFalse(UUID.randomUUID());

        assertThat(models).isEmpty();
    }

    @Test
    void findAllByIdInAndRemovedFalse_returnsRequestedModels() {
        List<CarModelJpaEntity> models = carModelRepository.findAllByIdInAndRemovedFalse(
                Set.of(MODEL_320I_ID, MODEL_M340I_ID)
        );

        assertThat(models).hasSize(2);
        assertThat(models).extracting(CarModelJpaEntity::getId)
                .containsExactlyInAnyOrder(MODEL_320I_ID, MODEL_M340I_ID);
    }

    @Test
    void findAllByIdInAndRemovedFalse_unknownIds_returnsEmpty() {
        List<CarModelJpaEntity> models = carModelRepository.findAllByIdInAndRemovedFalse(
                Set.of(UUID.randomUUID(), UUID.randomUUID())
        );

        assertThat(models).isEmpty();
    }

    @Test
    void markRemoved_modelExcludedFromActiveFinders() {
        CarModelJpaEntity model = carModelRepository.findByIdAndRemovedFalse(MODEL_320I_ID).orElseThrow();
        model.markRemoved();
        carModelRepository.saveAndFlush(model);

        Optional<CarModelJpaEntity> byId = carModelRepository.findByIdAndRemovedFalse(MODEL_320I_ID);
        List<CarModelJpaEntity> all = carModelRepository.findAllByRemovedFalse();

        assertThat(byId).isEmpty();
        assertThat(all).extracting(CarModelJpaEntity::getId).doesNotContain(MODEL_320I_ID);
    }
}
