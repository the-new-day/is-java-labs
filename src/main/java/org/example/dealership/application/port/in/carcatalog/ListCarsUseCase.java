package org.example.dealership.application.port.in.carcatalog;

import org.example.dealership.application.port.in.carcatalog.dto.CarFilterDto;
import org.example.dealership.application.port.in.carcatalog.dto.CarSummaryDto;

import java.util.List;
import java.util.UUID;

public interface ListCarsUseCase {
    Response execute(Request request);

    record Request(CarFilterDto filter) {}

    record Response(List<CarSummaryDto> carSummaryList) {}
}
