package org.dealership.infrastructure.persistence.jpa.mapper;

import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.configuration.ComponentVariant;
import org.dealership.domain.model.configuration.ComponentVariantSelection;
import org.dealership.domain.model.configuration.Configuration;
import org.dealership.domain.model.enums.ComponentType;
import org.dealership.infrastructure.persistence.jpa.entity.CarModelJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.ConfigurationComponentVariantJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.ConfigurationJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.ComponentVariantJpaEntity;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ConfigurationJpaMapper {

    private final CarModelJpaMapper carModelMapper;
    private final ComponentVariantJpaMapper componentVariantMapper;

    public ConfigurationJpaMapper(CarModelJpaMapper carModelMapper, ComponentVariantJpaMapper componentVariantMapper) {
        this.carModelMapper = carModelMapper;
        this.componentVariantMapper = componentVariantMapper;
    }

    public Configuration toDomain(ConfigurationJpaEntity entity) {
        CarModel model = carModelMapper.toDomain(entity.getCarModel());

        Map<ComponentType, ComponentVariant> variantMap = entity.getComponentVariants().stream()
                .filter(cv -> !cv.isRemoved())
                .filter(cv -> !cv.getComponentVariant().isRemoved())
                .collect(Collectors.toMap(
                        ConfigurationComponentVariantJpaEntity::getComponentType,
                        cv -> componentVariantMapper.toDomain(cv.getComponentVariant())
                ));

        return new Configuration(model, new ComponentVariantSelection(variantMap));
    }

    public ConfigurationJpaEntity toEntity(
            Configuration config,
            CarModelJpaEntity carModelEntity,
            Map<UUID, ComponentVariantJpaEntity> variantEntitiesById
    ) {
        ConfigurationJpaEntity entity = new ConfigurationJpaEntity(UUID.randomUUID(), carModelEntity);
        replaceComponentVariants(entity, config, variantEntitiesById);
        return entity;
    }

    public void updateEntity(
            ConfigurationJpaEntity entity,
            Configuration config,
            CarModelJpaEntity carModelEntity,
            Map<UUID, ComponentVariantJpaEntity> variantEntitiesById
    ) {
        entity.setCarModel(carModelEntity);
        replaceComponentVariants(entity, config, variantEntitiesById);
    }

    private void replaceComponentVariants(
            ConfigurationJpaEntity entity, Configuration config,
            Map<UUID, ComponentVariantJpaEntity> variantEntitiesById
    ) {
        Map<ComponentType, ConfigurationComponentVariantJpaEntity> existingByType = entity.getComponentVariants().stream()
                .collect(Collectors.toMap(ConfigurationComponentVariantJpaEntity::getComponentType, cv -> cv));

        Map<ComponentType, ComponentVariant> desired = config.getComponentVariantSelection().asMap();

        existingByType.entrySet().stream()
                .filter(e -> !desired.containsKey(e.getKey()))
                .map(Map.Entry::getValue)
                .forEach(entity::removeComponentVariant);

        for (Map.Entry<ComponentType, ComponentVariant> entry : desired.entrySet()) {
            ComponentType type = entry.getKey();
            ComponentVariantJpaEntity variantEntity = variantEntitiesById.get(entry.getValue().getId().value());
            ConfigurationComponentVariantJpaEntity existing = existingByType.get(type);
            if (existing != null) {
                existing.setComponentVariant(variantEntity);
            } else {
                entity.addComponentVariant(new ConfigurationComponentVariantJpaEntity(
                        UUID.randomUUID(), entity, type, variantEntity));
            }
        }
    }
}
