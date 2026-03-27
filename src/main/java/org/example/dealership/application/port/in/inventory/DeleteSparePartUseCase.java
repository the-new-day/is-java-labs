package org.example.dealership.application.port.in.inventory;

import java.util.UUID;

public interface DeleteSparePartUseCase {
    Response execute(Request request);

    record Request(UUID id) {}

    record Response() {}
}
