package org.example.dealership.application.port.in.inventory;

import org.example.dealership.application.port.in.inventory.dto.NewModelDto;

import java.util.UUID;

public interface AddModelUseCase {
    Response execute(Request request);

    record Request(NewModelDto newModel) {}

    record Response(UUID id) {}
}
