package org.dealership.application.port.in.stockorder;

import org.dealership.application.port.in.stockorder.dto.StockOrderDto;

import java.util.List;

public interface ListStockOrdersUseCase {
    Response execute(Request request);

    record Request() {}

    record Response(List<StockOrderDto> order) {}
}
