package org.example.dealership.application.port.in.customorder;

import org.example.dealership.application.port.in.customorder.dto.CustomOrderDto;

public interface UpdateCustomOrderUseCase {
    Response execute(Request request);

    record Request(CustomOrderDto order) {}

    record Response() {}
}
