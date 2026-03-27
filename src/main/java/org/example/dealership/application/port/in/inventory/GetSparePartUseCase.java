package org.example.dealership.application.port.in.inventory;

import org.example.dealership.application.port.in.inventory.dto.SparePartSummary;

import java.util.UUID;

public interface GetSparePartUseCase {
    Response execute(Request request);

    record Request(UUID sparePartId) {}

    record Response(SparePartSummary sparePartSummary) {}
}
