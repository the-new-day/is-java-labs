package org.dealership.application.port.in.inventory.dto;

import org.dealership.application.port.in.common.dto.CarBodyTypeDto;
import org.dealership.application.port.in.common.dto.ComponentTypeDto;
import org.dealership.application.port.in.common.dto.ComponentVariantSelectionDto;
import org.dealership.application.port.in.common.dto.DriveTypeDto;
import org.dealership.application.port.in.common.dto.FuelTypeDto;
import org.dealership.application.port.in.common.dto.MoneyDto;
import org.dealership.application.port.in.common.dto.TransmissionTypeDto;

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
