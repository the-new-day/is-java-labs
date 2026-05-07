package org.dealership.application.port.in.inventory.dto;

import org.dealership.application.port.in.common.dto.MoneyDto;

import java.util.Set;
import java.util.UUID;

public record SparePartSummaryDto(
        UUID id,
        String name,
        MoneyDto price,
        Set<UUID> compatibleModelIds
) {}
