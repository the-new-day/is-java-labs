package org.dealership.infrastructure.persistence.jpa.mapper;

import org.dealership.domain.model.assembly.AssemblyOrder;
import org.dealership.domain.model.assembly.PartRequirement;
import org.dealership.domain.model.id.AssemblyOrderId;
import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.id.CarModelId;
import org.dealership.domain.model.id.UserId;
import org.dealership.infrastructure.persistence.jpa.entity.AssemblyOrderJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.PartRequirementEmbeddable;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AssemblyOrderJpaMapper {

    public AssemblyOrder toDomain(AssemblyOrderJpaEntity entity) {
        return new AssemblyOrder(
                new AssemblyOrderId(entity.getId()),
                entity.getSourceOrderId(),
                entity.getSourceOrderType(),
                entity.getCarId() == null ? null : new CarId(entity.getCarId()),
                entity.getCarModelId() == null ? null : new CarModelId(entity.getCarModelId()),
                entity.getRequiredParts().stream()
                        .map(p -> new PartRequirement(p.getPartId(), p.getQuantity()))
                        .toList(),
                entity.getStatus(),
                entity.getAssignedWorkerId() == null ? null : new UserId(entity.getAssignedWorkerId())
        );
    }

    public AssemblyOrderJpaEntity toEntity(AssemblyOrder order) {
        AssemblyOrderJpaEntity entity = new AssemblyOrderJpaEntity(
                order.getId().value(),
                order.getSourceOrderId(),
                order.getSourceOrderType(),
                order.getCarId() == null ? null : order.getCarId().value(),
                order.getCarModelId() == null ? null : order.getCarModelId().value(),
                order.getStatus()
        );
        entity.setAssignedWorkerId(order.getAssignedWorkerId() == null ? null : order.getAssignedWorkerId().value());
        entity.replaceRequiredParts(toEmbeddables(order));
        return entity;
    }

    public void updateEntity(AssemblyOrderJpaEntity entity, AssemblyOrder order) {
        entity.setSourceOrderId(order.getSourceOrderId());
        entity.setSourceOrderType(order.getSourceOrderType());
        entity.setCarId(order.getCarId() == null ? null : order.getCarId().value());
        entity.setCarModelId(order.getCarModelId() == null ? null : order.getCarModelId().value());
        entity.setStatus(order.getStatus());
        entity.setAssignedWorkerId(order.getAssignedWorkerId() == null ? null : order.getAssignedWorkerId().value());
        entity.replaceRequiredParts(toEmbeddables(order));
    }

    private Set<PartRequirementEmbeddable> toEmbeddables(AssemblyOrder order) {
        return order.getRequiredParts().stream()
                .map(p -> new PartRequirementEmbeddable(p.getPartId(), p.getQuantity()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
