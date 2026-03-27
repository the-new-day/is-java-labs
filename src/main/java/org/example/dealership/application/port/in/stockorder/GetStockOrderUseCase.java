package org.example.dealership.application.port.in.stockorder;

import org.example.dealership.application.port.in.stockorder.dto.StockCarOrderDto;

import java.util.UUID;

public interface GetStockOrderUseCase {
    Response execute(Request request);

    record Request(UUID id) {}

    record Response(StockCarOrderDto order) {}
}
