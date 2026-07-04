package org.dealership.application.port.in.inventory;

import org.dealership.application.port.in.inventory.dto.SparePartSummaryDto;

import java.util.UUID;

public interface GetSparePartUseCase {
    Response execute(Request request);

    record Request(UUID sparePartId) {}

    record Response(SparePartSummaryDto sparePartSummaryDto) {}
}
