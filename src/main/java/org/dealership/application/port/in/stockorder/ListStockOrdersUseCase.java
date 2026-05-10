package org.dealership.application.port.in.stockorder;

import org.dealership.application.port.in.stockorder.dto.StockOrderDto;

import java.util.List;
import java.util.UUID;

public interface ListStockOrdersUseCase {
    Response execute(Request request);

    record Request(UUID clientIdFilter) {
        public Request() {
            this(null);
        }
    }

    record Response(List<StockOrderDto> order) {}
}
