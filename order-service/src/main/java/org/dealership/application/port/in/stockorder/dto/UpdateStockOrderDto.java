package org.dealership.application.port.in.stockorder.dto;

import java.util.UUID;

public record UpdateStockOrderDto(
        UUID clientId,
        UUID managerId,
        UUID carId,
        StockOrderStatusDto status) {
}
