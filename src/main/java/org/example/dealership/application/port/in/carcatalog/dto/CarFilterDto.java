package org.example.dealership.application.port.in.carcatalog.dto;

import org.example.dealership.application.port.in.common.dto.*;

import java.util.UUID;

public record CarFilterDto(
        MoneyDto minPrice,
        MoneyDto maxPrice,
        UUID brandId,
        UUID modelId,
        CarBodyTypeDto bodyType,
        FuelTypeDto fuelType,
        Integer minEnginePower,
        Integer maxEnginePower,
        Double minEngineVolume,
        Double maxEngineVolume,
        TransmissionTypeDto transmissionType,
        DriveTypeDto driveType,
        ColorDto color
) {}
