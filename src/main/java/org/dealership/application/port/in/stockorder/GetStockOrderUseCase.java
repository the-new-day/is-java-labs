package org.dealership.application.port.in.stockorder;

import org.dealership.application.port.in.stockorder.dto.StockOrderDto;

import java.util.UUID;

public interface GetStockOrderUseCase {
    Response execute(Request request);

    record Request(UUID id) {}

    record Response(StockOrderDto order) {}
}
