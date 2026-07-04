package org.dealership.application.port.in.stockorder;

import org.dealership.application.port.in.stockorder.dto.UpdateStockOrderDto;

import java.util.UUID;

public interface UpdateStockOrderUseCase {
    Response execute(Request request);

    record Request(UUID orderId, UpdateStockOrderDto order) {}

    record Response() {}
}
