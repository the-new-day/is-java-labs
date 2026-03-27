package org.example.dealership.application.port.in.inventory;

import org.example.dealership.application.port.in.common.dto.CarDetailsDto;

import java.util.UUID;

public interface GetCarUseCase {
    Response execute(Request request);

    record Request(UUID carId) {}

    record Response(CarDetailsDto carDetails) {}
}
