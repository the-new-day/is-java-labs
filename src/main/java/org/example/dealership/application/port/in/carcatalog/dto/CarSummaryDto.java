package org.example.dealership.application.port.in.carcatalog.dto;

import org.example.dealership.application.port.in.common.dto.CarModelDto;
import org.example.dealership.application.port.in.common.dto.ColorDto;
import org.example.dealership.application.port.in.common.dto.MoneyDto;

import java.util.UUID;

public record CarSummaryDto(
        UUID id,
        CarModelDto model,
        MoneyDto price,
        ColorDto color,
        boolean testDriveAvailable
) {}
