package org.dealership.application.port.in.common.dto;

public record ConfigurationDto(
        CarModelDto carModel,
        ComponentVariantSelectionDto componentVariantSelection
) {}
