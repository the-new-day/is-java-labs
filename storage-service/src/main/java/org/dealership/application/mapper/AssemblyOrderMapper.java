package org.dealership.application.mapper;

import org.dealership.application.port.in.assembly.dto.AssemblyOrderSummaryDto;
import org.dealership.application.port.in.assembly.dto.NewAssemblyOrderDto;
import org.dealership.application.port.in.assembly.dto.PartRequirementDto;
import org.dealership.domain.model.assembly.AssemblyOrder;
import org.dealership.domain.model.assembly.AssemblyOrderStatus;
import org.dealership.domain.model.assembly.PartRequirement;
import org.dealership.domain.model.assembly.SourceOrderType;
import org.dealership.domain.model.id.AssemblyOrderId;
import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.id.CarModelId;
import org.dealership.domain.model.id.UserId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AssemblyOrderMapper {

    public AssemblyOrder toDomain(AssemblyOrderId id, NewAssemblyOrderDto dto) {
        List<PartRequirement> parts = dto.requiredParts().stream()
                .map(p -> new PartRequirement(p.partId(), p.quantity()))
                .toList();
        return new AssemblyOrder(
                id,
                dto.sourceOrderId(),
                SourceOrderType.valueOf(dto.sourceOrderType()),
                dto.carId() == null ? null : new CarId(dto.carId()),
                dto.carModelId() == null ? null : new CarModelId(dto.carModelId()),
                parts,
                dto.status() == null ? AssemblyOrderStatus.CREATED : AssemblyOrderStatus.valueOf(dto.status()),
                dto.assignedWorkerId() == null ? null : new UserId(dto.assignedWorkerId())
        );
    }

    public AssemblyOrderSummaryDto toSummaryDto(AssemblyOrder order) {
        List<PartRequirementDto> parts = order.getRequiredParts().stream()
                .map(p -> new PartRequirementDto(p.getPartId(), p.getQuantity()))
                .toList();
        return new AssemblyOrderSummaryDto(
                order.getId().value(),
                order.getSourceOrderId(),
                order.getSourceOrderType().name(),
                order.getCarId() == null ? null : order.getCarId().value(),
                order.getCarModelId() == null ? null : order.getCarModelId().value(),
                parts,
                order.getStatus().name(),
                order.getAssignedWorkerId() == null ? null : order.getAssignedWorkerId().value()
        );
    }
}
