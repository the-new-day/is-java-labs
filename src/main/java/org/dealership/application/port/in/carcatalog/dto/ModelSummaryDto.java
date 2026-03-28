package org.dealership.application.port.in.carcatalog.dto;

import org.dealership.application.port.in.common.dto.MoneyDto;

import java.util.UUID;

public record ModelSummaryDto(
        UUID id,
        BrandDto brand,
        String name,
        MoneyDto basePrice
) {
}
