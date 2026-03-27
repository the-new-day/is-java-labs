package org.example.dealership.application.port.in.customorder;

import org.example.dealership.application.port.in.customorder.dto.CustomOrderDto;

import java.util.UUID;

public interface GetCustomOrderUseCase {
    Response execute(Request request);

    record Request(UUID id) {}

    record Response(CustomOrderDto order) {}
}
