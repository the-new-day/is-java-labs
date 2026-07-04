package org.dealership.application.port.in.inventory;

import org.dealership.application.port.in.inventory.dto.SparePartSummaryDto;

import java.util.List;

public interface ListSparePartsUseCase {
    Response execute(Request request);

    record Request() {}

    record Response(List<SparePartSummaryDto> sparePartSummaryDtoList) {}
}
