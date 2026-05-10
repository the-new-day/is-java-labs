package org.dealership.application.port.in.inventory;

import org.dealership.application.port.in.inventory.dto.NewSparePartDto;

import java.util.UUID;

public interface UpdateSparePartUseCase {
    Response execute(Request request);

    record Request(UUID sparePartId, NewSparePartDto newSparePartDto) {}

    record Response() {}
}
