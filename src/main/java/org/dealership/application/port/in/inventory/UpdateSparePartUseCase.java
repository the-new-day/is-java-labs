package org.dealership.application.port.in.inventory;

import org.dealership.application.port.in.inventory.dto.SparePartSummary;

public interface UpdateSparePartUseCase {
    Response execute(Request request);

    record Request(SparePartSummary sparePartSummary) {}

    record Response() {}
}
