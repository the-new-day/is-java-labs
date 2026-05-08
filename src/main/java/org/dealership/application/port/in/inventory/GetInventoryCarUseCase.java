package org.dealership.application.port.in.inventory;

import org.dealership.application.port.in.common.dto.CarDetailsDto;

import java.util.UUID;

public interface GetInventoryCarUseCase {
    Response execute(Request request);

    record Request(UUID carId) {}

    record Response(CarDetailsDto carDetails) {}
}
