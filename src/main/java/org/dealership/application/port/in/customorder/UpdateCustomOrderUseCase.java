package org.dealership.application.port.in.customorder;

import org.dealership.application.port.in.customorder.dto.CustomOrderDto;

public interface UpdateCustomOrderUseCase {
    Response execute(Request request);

    record Request(CustomOrderDto order) {}

    record Response() {}
}
