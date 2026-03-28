package org.dealership.application.port.in.stockorder;

import org.dealership.application.port.in.stockorder.dto.StockOrderDto;

public interface UpdateStockOrderUseCase {
    Response execute(Request request);

    record Request(StockOrderDto order) {}

    record Response() {}
}
