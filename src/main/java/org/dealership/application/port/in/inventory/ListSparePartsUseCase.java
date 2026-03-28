package org.dealership.application.port.in.inventory;

import org.dealership.application.port.in.inventory.dto.SparePartSummary;

import java.util.List;

public interface ListSparePartsUseCase {
    Response execute(Request request);

    record Request() {}

    record Response(List<SparePartSummary> sparePartSummaryList) {}
}
