package org.dealership.application.port.in.inventory;

import org.dealership.application.port.in.inventory.dto.CarSummaryDto;

import java.util.List;

public interface ListCarsUseCase {
    Response execute(Request request);

    record Request() {}

    record Response(List<CarSummaryDto> carSummaryList) {}
}
