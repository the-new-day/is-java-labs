package org.example.dealership.application.port.in.inventory.dto;

import org.example.dealership.application.port.in.common.dto.CarBodyTypeDto;
import org.example.dealership.application.port.in.common.dto.ComponentTypeDto;
import org.example.dealership.application.port.in.common.dto.ComponentVariantSelectionDto;
import org.example.dealership.application.port.in.common.dto.DriveTypeDto;
import org.example.dealership.application.port.in.common.dto.FuelTypeDto;
import org.example.dealership.application.port.in.common.dto.MoneyDto;
import org.example.dealership.application.port.in.common.dto.TransmissionTypeDto;

import java.util.Set;
import java.util.UUID;

public record NewModelDto(
        UUID brandId,
        String name,
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
