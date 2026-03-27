package org.example.dealership.application.port.in.stockorder;

import org.example.dealership.application.port.in.stockorder.dto.StockCarOrderDto;

public interface UpdateStockOrderUseCase {
    Response execute(Request request);

    record Request(StockCarOrderDto order) {}

    record Response() {}
}
