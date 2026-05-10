package org.dealership.application.port.in.common.dto;

import java.util.UUID;

public record CarDetailsDto(
        UUID id,
        ConfigurationDto configuration,
        MoneyDto price,
        ColorDto color,
        boolean testDriveAvailable
) {}
