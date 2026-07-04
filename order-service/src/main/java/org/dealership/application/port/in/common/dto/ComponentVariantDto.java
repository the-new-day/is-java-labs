package org.dealership.application.port.in.common.dto;

import java.util.Set;
import java.util.UUID;

public record ComponentVariantDto(
        UUID id,
        ComponentTypeDto type,
        String name,
        MoneyDto surcharge,
        Set<UUID> carModelIds
) {
}
