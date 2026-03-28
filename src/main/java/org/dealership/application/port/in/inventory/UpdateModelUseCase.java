package org.dealership.application.port.in.inventory;

import org.dealership.application.port.in.common.dto.CarModelDto;

public interface UpdateModelUseCase {
    Response execute(Request request);

    record Request(CarModelDto model) {}

    record Response() {}
}
