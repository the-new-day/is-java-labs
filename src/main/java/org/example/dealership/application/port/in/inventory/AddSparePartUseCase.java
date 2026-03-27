package org.example.dealership.application.port.in.inventory;

import org.example.dealership.application.port.in.inventory.dto.NewSparePartDto;

import java.util.UUID;

public interface AddSparePartUseCase {
    Response execute(Request request);

    record Request(NewSparePartDto newSparePart) {}

    record Response(UUID id) {}
}
