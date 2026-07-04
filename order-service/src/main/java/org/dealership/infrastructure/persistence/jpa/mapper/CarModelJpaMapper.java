package org.dealership.infrastructure.persistence.jpa.mapper;

import org.dealership.domain.model.car.Brand;
import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.configuration.ComponentVariant;
import org.dealership.domain.model.configuration.ComponentVariantSelection;
import org.dealership.domain.model.enums.ComponentType;
import org.dealership.domain.model.id.CarModelId;
import org.dealership.domain.model.vo.Money;
import org.dealership.infrastructure.persistence.jpa.entity.BrandJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.CarModelBaseComponentJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.CarModelConfigurableComponentJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.CarModelJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.ComponentVariantJpaEntity;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CarModelJpaMapper {
    private final BrandJpaMapper brandMapper;
    private final ComponentVariantJpaMapper componentVariantMapper;

    public CarModelJpaMapper(BrandJpaMapper brandMapper, ComponentVariantJpaMapper componentVariantMapper) {
        this.brandMapper = brandMapper;
        this.componentVariantMapper = componentVariantMapper;
    }

    public CarModel toDomain(CarModelJpaEntity entity) {
        Brand brand = brandMapper.toDomain(entity.getBrand());

        Map<ComponentType, ComponentVariant> baseVariantsMap = entity.getBaseComponents().stream()
                .filter(bc -> !bc.isRemoved())
                .filter(bc -> !bc.getComponentVariant().isRemoved())
                .collect(Collectors.toMap(
                        CarModelBaseComponentJpaEntity::getComponentType,
                        bc -> componentVariantMapper.toDomain(bc.getComponentVariant())
                ));

        Set<ComponentType> configurableTypes = entity.getConfigurableComponents().stream()
                .filter(cc -> !cc.isRemoved())
                .map(CarModelConfigurableComponentJpaEntity::getComponentType)
                .collect(Collectors.toUnmodifiableSet());

        return new CarModel(
                new CarModelId(entity.getId()),
                brand,
                entity.getModelName(),
                new Money(entity.getBasePrice()),
                entity.getBodyType(),
                entity.getFuelType(),
                entity.getDriveType(),
                entity.getEngineVolume(),
                entity.getEnginePower(),
                entity.getBaseTransmissionType(),
                new ComponentVariantSelection(baseVariantsMap),
                configurableTypes
        );
    }

    public CarModelJpaEntity toEntity(
            CarModel model,
            BrandJpaEntity brandEntity,
            Map<UUID, ComponentVariantJpaEntity> variantEntitiesById
    ) {
        CarModelJpaEntity entity = new CarModelJpaEntity(
                model.getId().value(),
                brandEntity,
                model.getModelName(),
                model.getBasePrice().asBigDecimal(),
                model.getCarBodyType(),
                model.getFuelType(),
                model.getDriveType(),
                model.getEngineVolume(),
                model.getEnginePower(),
                model.getBaseTransmissionType()
        );
        replaceBaseComponents(entity, model, variantEntitiesById);
        replaceConfigurableComponents(entity, model);
        return entity;
    }

    public void updateEntity(
            CarModelJpaEntity entity,
            CarModel model,
            BrandJpaEntity brandEntity,
            Map<UUID, ComponentVariantJpaEntity> variantEntitiesById
    ) {
        entity.setBrand(brandEntity);
        entity.setModelName(model.getModelName());
        entity.setBasePrice(model.getBasePrice().asBigDecimal());
        entity.setBodyType(model.getCarBodyType());
        entity.setFuelType(model.getFuelType());
        entity.setDriveType(model.getDriveType());
        entity.setEngineVolume(model.getEngineVolume());
        entity.setEnginePower(model.getEnginePower());
        entity.setBaseTransmissionType(model.getBaseTransmissionType());
        replaceBaseComponents(entity, model, variantEntitiesById);
        replaceConfigurableComponents(entity, model);
    }

    private void replaceBaseComponents(
            CarModelJpaEntity entity,
            CarModel model,
            Map<UUID, ComponentVariantJpaEntity> variantEntitiesById
    ) {
        Set<CarModelBaseComponentJpaEntity> baseComponents
                = model.getBaseComponentSelection().asMap().entrySet().stream()
                .map(e -> new CarModelBaseComponentJpaEntity(
                        UUID.randomUUID(),
                        entity,
                        e.getKey(),
                        variantEntitiesById.get(e.getValue().getId().value())
                ))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        entity.replaceBaseComponents(baseComponents);
    }

    private void replaceConfigurableComponents(CarModelJpaEntity entity, CarModel model) {
        Set<CarModelConfigurableComponentJpaEntity> configurableComponents
                = model.getConfigurableComponentTypes().stream()
                .map(type -> new CarModelConfigurableComponentJpaEntity(
                        UUID.randomUUID(),
                        entity, type
                ))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        entity.replaceConfigurableComponents(configurableComponents);
    }
}
