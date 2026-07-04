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
        Map<UUID, SparePartCompatibilityJpaEntity> existingByModelId = entity.getCompatibleModels().stream()
                .collect(Collectors.toMap(c -> c.getCarModel().getId(), c -> c));

        Set<UUID> desired = part.getCompatibleModelIds().stream()
                .map(CarModelId::value)
                .collect(Collectors.toSet());

        existingByModelId.entrySet().stream()
                .filter(e -> !desired.contains(e.getKey()))
                .map(Map.Entry::getValue)
                .forEach(entity::removeCompatibleModel);

        for (UUID modelId : desired) {
            if (!existingByModelId.containsKey(modelId)) {
                entity.addCompatibleModel(new SparePartCompatibilityJpaEntity(
                        UUID.randomUUID(), entity, compatibleModelsById.get(modelId)));
            }
        }
    }
}
