package org.dealership.application.port.in.inventory;

import org.dealership.application.port.in.inventory.dto.NewModelDto;

import java.util.UUID;

public interface AddModelUseCase {
    Response execute(Request request);

    record Request(NewModelDto newModel) {}

    record Response(UUID id) {}
}
