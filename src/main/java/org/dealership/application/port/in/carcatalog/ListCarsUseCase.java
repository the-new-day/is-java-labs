package org.dealership.application.port.in.carcatalog;

import org.dealership.application.port.in.carcatalog.dto.CarFilterDto;
import org.dealership.application.port.in.carcatalog.dto.CarSummaryDto;

import java.util.List;

public interface ListCarsUseCase {
    Response execute(Request request);

    record Request(CarFilterDto filter) {}

    record Response(List<CarSummaryDto> carSummaryList) {}
}
