package org.dealership.infrastructure.persistence.jpa.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import org.dealership.domain.model.assembly.AssemblyOrderStatus;
import org.dealership.domain.model.assembly.SourceOrderType;
import org.hibernate.annotations.BatchSize;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "assembly_order")
public class AssemblyOrderJpaEntity extends BaseJpaEntity {

    @Column(name = "source_order_id", nullable = false)
    private UUID sourceOrderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_order_type", nullable = false, length = 40)
    private SourceOrderType sourceOrderType;

    @Column(name = "car_id")
    private UUID carId;

    @Column(name = "car_model_id")
    private UUID carModelId;

    @Column(name = "assigned_worker_id")
    private UUID assignedWorkerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private AssemblyOrderStatus status;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "assembly_order_parts",
            joinColumns = @JoinColumn(name = "assembly_order_id", nullable = false)
    )
    @BatchSize(size = 32)
    private Set<PartRequirementEmbeddable> requiredParts = new LinkedHashSet<>();

    protected AssemblyOrderJpaEntity() {
    }

    public AssemblyOrderJpaEntity(
            UUID id,
            UUID sourceOrderId,
            SourceOrderType sourceOrderType,
            UUID carId,
            UUID carModelId,
            AssemblyOrderStatus status
    ) {
        super(id);
        this.sourceOrderId = sourceOrderId;
        this.sourceOrderType = sourceOrderType;
        this.carId = carId;
        this.carModelId = carModelId;
        this.status = status;
    }

    public UUID getSourceOrderId() {
        return sourceOrderId;
    }

    public SourceOrderType getSourceOrderType() {
        return sourceOrderType;
    }

    public UUID getCarId() {
        return carId;
    }

    public UUID getCarModelId() {
        return carModelId;
    }

    public UUID getAssignedWorkerId() {
        return assignedWorkerId;
    }

    public AssemblyOrderStatus getStatus() {
        return status;
    }

    public Set<PartRequirementEmbeddable> getRequiredParts() {
        return requiredParts;
    }

    public void setSourceOrderId(UUID sourceOrderId) {
        this.sourceOrderId = sourceOrderId;
    }

    public void setSourceOrderType(SourceOrderType sourceOrderType) {
        this.sourceOrderType = sourceOrderType;
    }

    public void setCarId(UUID carId) {
        this.carId = carId;
    }

    public void setCarModelId(UUID carModelId) {
        this.carModelId = carModelId;
    }

    public void setAssignedWorkerId(UUID assignedWorkerId) {
        this.assignedWorkerId = assignedWorkerId;
    }

    public void setStatus(AssemblyOrderStatus status) {
        this.status = status;
    }

    public void replaceRequiredParts(Set<PartRequirementEmbeddable> newParts) {
        requiredParts.clear();
        requiredParts.addAll(newParts);
    }
}
