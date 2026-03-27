package org.example.dealership.application.port.in.stockorder.dto;

import java.util.UUID;

public record StockCarOrderDto(
        UUID id,
        UUID clientId,
        UUID managerId,
        UUID carId,
        StockCarOrderStatusDto status
) {}
