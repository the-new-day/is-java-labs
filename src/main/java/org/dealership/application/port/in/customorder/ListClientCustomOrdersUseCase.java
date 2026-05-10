package org.dealership.application.port.in.customorder;

import org.dealership.application.port.in.customorder.dto.CustomOrderDto;

import java.util.List;
import java.util.UUID;

public interface ListClientCustomOrdersUseCase {
    Response execute(Request request);

    record Request(UUID clientId) {}

    record Response(List<CustomOrderDto> order) {}
}
