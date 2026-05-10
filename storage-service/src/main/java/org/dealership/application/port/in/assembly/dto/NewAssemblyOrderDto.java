package org.dealership.application.port.in.assembly.dto;

import java.util.List;
import java.util.UUID;

public record NewAssemblyOrderDto(
        UUID sourceOrderId,
        String sourceOrderType,
        UUID carId,
        UUID carModelId,
        List<PartRequirementDto> requiredParts,
        String status,
        UUID assignedWorkerId
) {
}
