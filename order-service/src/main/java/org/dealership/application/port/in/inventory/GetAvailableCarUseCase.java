package org.dealership.application.port.in.inventory;

import org.dealership.application.port.in.inventory.dto.AvailableCarDto;

import java.util.UUID;

public interface GetAvailableCarUseCase {
    Response execute(Request request);

    record Request(UUID id) {}

    record Response(AvailableCarDto car) {}
}
