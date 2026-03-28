package org.dealership.application.port.in.carcatalog;

import org.dealership.application.port.in.common.dto.CarModelDto;

import java.util.UUID;

public interface GetModelUseCase {
    Response execute(Request request);

    record Request(UUID modelId) {}

    record Response(CarModelDto model) {}
}
