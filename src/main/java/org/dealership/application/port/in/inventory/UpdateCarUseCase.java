package org.dealership.application.port.in.inventory;

import org.dealership.application.port.in.common.dto.CarDetailsDto;

public interface UpdateCarUseCase {
    Response execute(Request request);

    record Request(CarDetailsDto model) {}

    record Response() {}
}
