package org.dealership.application.port.in.inventory.dto;

import org.dealership.application.port.in.common.dto.ColorDto;
import org.dealership.application.port.in.common.dto.ConfigurationDto;
import org.dealership.application.port.in.common.dto.VinNumberDto;

public record NewCarDetailsDto(
        VinNumberDto vinNumber,
        ConfigurationDto configuration,
        ColorDto color
) {
}
