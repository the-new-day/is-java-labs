package org.example.dealership.application.port.in.stockorder.dto;

import java.util.UUID;

public record StockOrderDto(
        UUID id,
        UUID clientId,
        UUID managerId,
        UUID carId,
        StockOrderStatusDto status
) {}
