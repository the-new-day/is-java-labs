package org.dealership.infrastructure.persistence.jpa.mapper;

import org.dealership.domain.model.configuration.ComponentVariant;
import org.dealership.domain.model.id.CarModelId;
import org.dealership.domain.model.id.ComponentVariantId;
import org.dealership.domain.model.vo.Money;
import org.dealership.infrastructure.persistence.jpa.entity.CarModelJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.ComponentVariantCompatibilityJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.ComponentVariantJpaEntity;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ComponentVariantJpaMapper {
    public ComponentVariant toDomain(ComponentVariantJpaEntity entity) {
        Set<CarModelId> compatibleModelIds = entity.getCompatibleModels().stream()
                .filter(compatibility -> !compatibility.isRemoved())
                .filter(compatibility -> !compatibility.getCarModel().isRemoved())
                .map(compatibility -> new CarModelId(compatibility.getCarModel().getId()))
                .collect(Collectors.toUnmodifiableSet());

        return new ComponentVariant(
                new ComponentVariantId(entity.getId()),
                entity.getComponentType(),
                entity.getName(),
                new Money(entity.getSurcharge()),
                compatibleModelIds
        );
    }

    public ComponentVariantJpaEntity toEntity(
            ComponentVariant variant,
            Map<UUID, CarModelJpaEntity> compatibleModelsById
    ) {
        ComponentVariantJpaEntity entity = new ComponentVariantJpaEntity(
                variant.getId().value(),
                variant.getComponentType(),
                variant.getName(),
                variant.getSurcharge().asBigDecimal()
        );
        replaceCompatibleModels(entity, variant, compatibleModelsById);
        return entity;
    }

    public void updateEntity(
            ComponentVariantJpaEntity entity,
            ComponentVariant variant,
            Map<UUID, CarModelJpaEntity> compatibleModelsById
    ) {
        entity.setComponentType(variant.getComponentType());
        entity.setName(variant.getName());
        entity.setSurcharge(variant.getSurcharge().asBigDecimal());
        replaceCompatibleModels(entity, variant, compatibleModelsById);
    }

    private void replaceCompatibleModels(
            ComponentVariantJpaEntity entity,
            ComponentVariant variant,
            Map<UUID, CarModelJpaEntity> compatibleModelsById
    ) {
        Set<ComponentVariantCompatibilityJpaEntity> compatibilities = variant.getCompatibleModelIds().stream()
                .map(CarModelId::value)
                .map(compatibleModelsById::get)
                .map(model -> new ComponentVariantCompatibilityJpaEntity(UUID.randomUUID(), entity, model))
                .collect(Collectors.toCollection(java.util.LinkedHashSet::new));
        entity.replaceCompatibleModels(compatibilities);
    }
}
