package org.example.dealership.application.port.in.stockorder;

import org.example.dealership.application.port.in.stockorder.dto.StockCarOrderDto;

import java.util.List;

public interface ListStockCarOrdersUseCase {
    Response execute(Request request);

    record Request() {}

    record Response(List<StockCarOrderDto> order) {}
}
