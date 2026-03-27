package org.example.dealership.application.port.in.customorder;

import org.example.dealership.application.port.in.customorder.dto.CustomOrderDto;

import java.util.List;

public interface ListCustomOrdersUseCase {
    Response execute(Request request);

    record Request() {}

    record Response(List<CustomOrderDto> order) {}
}
