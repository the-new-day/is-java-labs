package org.dealership.application.port.in.customorder.dto;

import java.util.UUID;

public record CustomOrderDto(
        UUID id,
        UUID clientId,
        UUID managerId,
        UUID carId,
        CustomOrderStatusDto status
) {}
