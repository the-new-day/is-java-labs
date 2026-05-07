package org.dealership.application.port.in.inventory;

import org.dealership.application.port.in.inventory.dto.SparePartSummaryDto;

public interface UpdateSparePartUseCase {
    Response execute(Request request);

    record Request(SparePartSummaryDto sparePartSummaryDto) {}

    record Response() {}
}
