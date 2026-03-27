package org.example.dealership.application.port.in.inventory.dto;

import org.example.dealership.application.port.in.common.dto.ColorDto;
import org.example.dealership.application.port.in.common.dto.ConfigurationDto;
import org.example.dealership.application.port.in.common.dto.VinNumberDto;

public record NewCarDetailsDto(
        VinNumberDto vinNumber,
        ConfigurationDto configuration,
        ColorDto colorDto
) {
}
