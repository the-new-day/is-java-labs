package org.dealership.persistence;

import org.dealership.AbstractIntegrationTest;
import org.dealership.infrastructure.persistence.jpa.entity.ConfigurationJpaEntity;
import org.dealership.infrastructure.persistence.jpa.repository.ConfigurationJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class ConfigurationRepositoryIT extends AbstractIntegrationTest {

    private static final UUID CONFIG_401_ID = UUID.fromString("00000000-0000-0000-0000-000000000401");
    private static final UUID CONFIG_402_ID = UUID.fromString("00000000-0000-0000-0000-000000000402");
    private static final UUID MODEL_320I_ID = UUID.fromString("00000000-0000-0000-0000-000000000101");
    private static final UUID MODEL_330I_ID = UUID.fromString("00000000-0000-0000-0000-000000000102");

    @Autowired
    private ConfigurationJpaRepository configurationRepository;

    @Test
    void findByIdAndRemovedFalse_existingConfig_returnsConfigWithModelAndBrand() {
        Optional<ConfigurationJpaEntity> result = configurationRepository.findByIdAndRemovedFalse(CONFIG_401_ID);

        assertThat(result).isPresent();
        ConfigurationJpaEntity config = result.get();
        assertThat(config.getCarModel()).isNotNull();
        assertThat(config.getCarModel().getId()).isEqualTo(MODEL_320I_ID);
        assertThat(config.getCarModel().getBrand()).isNotNull();
        assertThat(config.getCarModel().getBrand().getName()).isEqualTo("BMW");
    }

    @Test
    void findByIdAndRemovedFalse_nonExistingId_returnsEmpty() {
        Optional<ConfigurationJpaEntity> result = configurationRepository.findByIdAndRemovedFalse(UUID.randomUUID());

        assertThat(result).isEmpty();
    }

    @Test
    void findAllByRemovedFalse_returnsAllSeedConfigs() {
        List<ConfigurationJpaEntity> configs = configurationRepository.findAllByRemovedFalse();

        assertThat(configs).hasSize(2);
        assertThat(configs).extracting(ConfigurationJpaEntity::getId)
                .containsExactlyInAnyOrder(CONFIG_401_ID, CONFIG_402_ID);
    }

    @Test
    void findAllByCarModelIdAndRemovedFalse_model320i_returnsBothConfigs() {
        List<ConfigurationJpaEntity> configs = configurationRepository.findAllByCarModelIdAndRemovedFalse(MODEL_320I_ID);

        assertThat(configs).hasSize(2);
        assertThat(configs).allMatch(c -> c.getCarModel().getId().equals(MODEL_320I_ID));
    }

    @Test
    void findAllByCarModelIdAndRemovedFalse_modelWithoutConfigs_returnsEmpty() {
        List<ConfigurationJpaEntity> configs = configurationRepository.findAllByCarModelIdAndRemovedFalse(MODEL_330I_ID);

        assertThat(configs).isEmpty();
    }

    @Test
    void findAllByCarModelIdAndRemovedFalse_unknownModel_returnsEmpty() {
        List<ConfigurationJpaEntity> configs = configurationRepository.findAllByCarModelIdAndRemovedFalse(UUID.randomUUID());

        assertThat(configs).isEmpty();
    }

    @Test
    void markRemoved_configExcludedFromActiveFinders() {
        ConfigurationJpaEntity config = configurationRepository.findByIdAndRemovedFalse(CONFIG_401_ID).orElseThrow();
        config.markRemoved();
        configurationRepository.saveAndFlush(config);

        Optional<ConfigurationJpaEntity> byId = configurationRepository.findByIdAndRemovedFalse(CONFIG_401_ID);
        List<ConfigurationJpaEntity> all = configurationRepository.findAllByRemovedFalse();

        assertThat(byId).isEmpty();
        assertThat(all).extracting(ConfigurationJpaEntity::getId).doesNotContain(CONFIG_401_ID);
    }
}
