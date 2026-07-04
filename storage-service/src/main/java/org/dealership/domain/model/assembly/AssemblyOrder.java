package org.dealership.domain.model.assembly;

import org.dealership.domain.model.id.AssemblyOrderId;
import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.id.CarModelId;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.validation.DomainChecks;

import java.util.List;
import java.util.UUID;

public class AssemblyOrder {
    private final AssemblyOrderId id;
    private final UUID sourceOrderId;
    private final SourceOrderType sourceOrderType;
    private final CarId carId;
    private final CarModelId carModelId;
    private final List<PartRequirement> requiredParts;
    private AssemblyOrderStatus status;
    private UserId assignedWorkerId;

    public AssemblyOrder(
            AssemblyOrderId id,
            UUID sourceOrderId,
            SourceOrderType sourceOrderType,
            CarId carId,
            CarModelId carModelId,
            List<PartRequirement> requiredParts,
            AssemblyOrderStatus status,
            UserId assignedWorkerId
    ) {
        this.id = DomainChecks.notNull(id, "assemblyOrderId");
        this.sourceOrderId = DomainChecks.notNull(sourceOrderId, "sourceOrderId");
        this.sourceOrderType = DomainChecks.notNull(sourceOrderType, "sourceOrderType");
        DomainChecks.require(carId != null || carModelId != null,
                "carId or carModelId must be provided");
        this.carId = carId;
        this.carModelId = carModelId;
        this.requiredParts = List.copyOf(DomainChecks.notEmpty(requiredParts, "requiredParts"));
        this.status = DomainChecks.notNull(status, "status");
        this.assignedWorkerId = assignedWorkerId;
    }

    public AssemblyOrderId getId() {
        return id;
    }

    public UUID getSourceOrderId() {
        return sourceOrderId;
    }

    public SourceOrderType getSourceOrderType() {
        return sourceOrderType;
    }

    public CarId getCarId() {
        return carId;
    }

    public CarModelId getCarModelId() {
        return carModelId;
    }

    public List<PartRequirement> getRequiredParts() {
        return List.copyOf(requiredParts);
    }

    public AssemblyOrderStatus getStatus() {
        return status;
    }

    public void changeStatus(AssemblyOrderStatus newStatus) {
        this.status = DomainChecks.notNull(newStatus, "status");
    }

    public UserId getAssignedWorkerId() {
        return assignedWorkerId;
    }

    public void assignTo(UserId workerId) {
        this.assignedWorkerId = workerId;
    }
}
