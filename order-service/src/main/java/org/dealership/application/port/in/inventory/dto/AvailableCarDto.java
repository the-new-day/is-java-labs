package org.dealership.application.port.in.inventory.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record AvailableCarDto(
        UUID id,
        String brandName,
        String modelName,
        String color,
        BigDecimal priceAmount
) {}
