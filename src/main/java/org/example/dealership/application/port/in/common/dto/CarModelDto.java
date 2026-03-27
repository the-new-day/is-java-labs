package org.example.dealership.application.port.in.common.dto;

import org.example.dealership.application.port.in.carcatalog.dto.BrandDto;

import java.util.Set;
import java.util.UUID;

public record CarModelDto(
        UUID id,
        BrandDto brand,
        String modelName,
        MoneyDto basePrice,
        CarBodyTypeDto carBodyType,
        FuelTypeDto fuelType,
        DriveTypeDto driveType,
        double engineVolume,
        int enginePower,
        TransmissionTypeDto baseTransmissionType,
        ComponentVariantSelectionDto baseComponentSelection,
        Set<ComponentTypeDto> configurableComponentTypes
) {}
