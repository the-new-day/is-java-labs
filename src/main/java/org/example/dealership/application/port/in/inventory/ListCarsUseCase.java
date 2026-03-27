package org.example.dealership.application.port.in.inventory;

import org.example.dealership.application.port.in.carcatalog.dto.ModelSummaryDto;
import org.example.dealership.application.port.in.inventory.dto.CarSummaryDto;

import java.util.List;

public interface ListCarsUseCase {
    Response execute(Request request);

    record Request() {}

    record Response(List<CarSummaryDto> carSummaryList) {}
}
