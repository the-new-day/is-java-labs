package org.example.dealership.application.port.in.stockorder;

import java.util.UUID;

public interface CreateStockOrderUseCase {
    Response execute(Request request);

    record Request(UUID clientId,
                   UUID carId) {}

    record Response(UUID id) {}
}
