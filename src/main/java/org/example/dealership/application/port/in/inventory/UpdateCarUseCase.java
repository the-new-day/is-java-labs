package org.example.dealership.application.port.in.inventory;

import org.example.dealership.application.port.in.common.dto.CarDetailsDto;

public interface UpdateCarUseCase {
    Response execute(Request request);

    record Request(CarDetailsDto model) {}

    record Response() {}
}
