package org.dealership.application.port.in.inventory;

import org.dealership.application.port.in.inventory.dto.NewModelDto;

import java.util.UUID;

public interface UpdateModelUseCase {
    Response execute(Request request);

    record Request(UUID modelId, NewModelDto model) {}

    record Response() {}
}
