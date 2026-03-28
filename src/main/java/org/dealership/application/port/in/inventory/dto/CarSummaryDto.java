package org.dealership.application.port.in.inventory.dto;

import org.dealership.application.port.in.common.dto.CarModelDto;
import org.dealership.application.port.in.common.dto.ColorDto;
import org.dealership.application.port.in.common.dto.MoneyDto;

import java.util.UUID;

public record CarSummaryDto(
        UUID id,
        CarModelDto model,
        MoneyDto price,
        ColorDto color
) {}
