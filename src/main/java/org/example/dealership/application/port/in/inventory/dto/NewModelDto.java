package org.example.dealership.application.port.in.inventory.dto;

import java.util.UUID;

public record NewModelDto(
        UUID brandId,
        String name
) {
}
