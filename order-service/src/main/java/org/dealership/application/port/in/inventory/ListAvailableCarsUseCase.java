package org.dealership.application.port.in.inventory;

import org.dealership.application.port.in.inventory.dto.AvailableCarDto;

import java.util.List;

public interface ListAvailableCarsUseCase {
    Response execute(Request request);

    record Request() {}

    record Response(List<AvailableCarDto> cars) {}
}
