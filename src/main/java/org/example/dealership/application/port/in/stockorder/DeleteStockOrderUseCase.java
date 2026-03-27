package org.example.dealership.application.port.in.stockorder;

import java.util.UUID;

public interface DeleteStockOrderUseCase {
    Response execute(Request request);

    record Request(UUID id) {}

    record Response() {}
}
