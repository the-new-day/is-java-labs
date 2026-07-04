package org.dealership.application.port.in.stockorder;

import org.dealership.application.port.in.stockorder.dto.StockOrderDto;

import java.util.List;
import java.util.UUID;

public interface ListClientStockOrdersUseCase {
    Response execute(Request request);

    record Request(UUID clientId) {}

    record Response(List<StockOrderDto> order) {}
}
