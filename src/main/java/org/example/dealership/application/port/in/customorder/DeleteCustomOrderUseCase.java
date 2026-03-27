package org.example.dealership.application.port.in.customorder;

import java.util.UUID;

public interface DeleteCustomOrderUseCase {
    Response execute(Request request);

    record Request(UUID id) {}

    record Response() {}
}
