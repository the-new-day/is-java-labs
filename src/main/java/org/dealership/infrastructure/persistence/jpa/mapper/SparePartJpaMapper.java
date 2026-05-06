package org.dealership.infrastructure.persistence.jpa.mapper;

import org.dealership.domain.model.id.CarModelId;
import org.dealership.domain.model.id.SparePartId;
import org.dealership.domain.model.part.SparePart;
import org.dealership.domain.model.vo.Money;
import org.dealership.infrastructure.persistence.jpa.entity.CarModelJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.SparePartCompatibilityJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.SparePartJpaEntity;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class SparePartJpaMapper {
    public SparePart toDomain(SparePartJpaEntity entity) {
        Set<CarModelId> compatibleModelIds = entity.getCompatibleModels().stream()
                .filter(compatibility -> !compatibility.isRemoved())
                .filter(compatibility -> !compatibility.getCarModel().isRemoved())
                .map(compatibility -> new CarModelId(compatibility.getCarModel().getId()))
                .collect(Collectors.toUnmodifiableSet());

        return new SparePart(
                new SparePartId(entity.getId()),
                entity.getName(),
                new Money(entity.getPrice()),
                compatibleModelIds
        );
    }

    public SparePartJpaEntity toEntity(
            SparePart part,
            Map<UUID, CarModelJpaEntity> compatibleModelsById
    ) {
        SparePartJpaEntity entity = new SparePartJpaEntity(
                part.getId().value(),
                part.getName(),
                part.getPrice().asBigDecimal()
        );
        replaceCompatibleModels(entity, part, compatibleModelsById);
        return entity;
    }

    public void updateEntity(
            SparePartJpaEntity entity,
            SparePart part,
            Map<UUID, CarModelJpaEntity> compatibleModelsById
    ) {
        entity.setName(part.getName());
        entity.setPrice(part.getPrice().asBigDecimal());
        replaceCompatibleModels(entity, part, compatibleModelsById);
    }

    private void replaceCompatibleModels(
            SparePartJpaEntity entity,
            SparePart part,
            Map<UUID, CarModelJpaEntity> compatibleModelsById
    ) {
        Set<SparePartCompatibilityJpaEntity> compatibilities = part.getCompatibleModelIds().stream()
                .map(CarModelId::value)
                .map(compatibleModelsById::get)
                .map(model -> new SparePartCompatibilityJpaEntity(UUID.randomUUID(), entity, model))
                .collect(Collectors.toCollection(java.util.LinkedHashSet::new));
        entity.replaceCompatibleModels(compatibilities);
    }
}
