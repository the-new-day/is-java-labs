package org.example.dealership.application.port.in.inventory;

import org.example.dealership.application.port.in.inventory.dto.NewCarDetailsDto;

import java.util.UUID;

public interface AddCarUseCase {
    Response execute(Request request);

    record Request(NewCarDetailsDto newCar) {}

    record Response(UUID id) {}
}
