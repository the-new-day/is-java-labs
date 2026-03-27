package org.example.dealership.application.port.in.carcatalog;

import org.example.dealership.application.port.in.common.dto.CarModelDto;

import java.util.UUID;

public interface GetModelUseCase {
    Response execute(Request request);

    record Request(UUID modelId) {}

    record Response(CarModelDto model) {}
}
