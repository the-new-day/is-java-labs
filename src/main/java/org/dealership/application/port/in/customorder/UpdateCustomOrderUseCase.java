package org.dealership.application.port.in.customorder;

import org.dealership.application.port.in.customorder.dto.UpdateCustomOrderDto;

import java.util.UUID;

public interface UpdateCustomOrderUseCase {
    Response execute(Request request);

    record Request(UUID customOrderId, UpdateCustomOrderDto order) {}

    record Response() {}
}
