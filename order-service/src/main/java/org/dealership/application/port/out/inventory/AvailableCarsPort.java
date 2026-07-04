package org.dealership.application.port.out.inventory;

import org.dealership.application.port.in.inventory.dto.AvailableCarDto;

import java.util.List;
import java.util.UUID;

public interface AvailableCarsPort {
    List<AvailableCarDto> listAvailableCars();
    AvailableCarDto getAvailableCar(UUID id);
}
