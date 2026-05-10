package org.dealership.application.port.in.inventory;

import org.dealership.application.port.in.inventory.dto.NewCarDetailsDto;

import java.util.UUID;

public interface UpdateCarUseCase {
    Response execute(Request request);

    record Request(UUID carId, NewCarDetailsDto newCarDetails) {}

    record Response() {}
}
