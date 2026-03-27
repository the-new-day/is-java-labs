package org.example.dealership.application.mapping;

import org.example.dealership.application.port.in.common.dto.FuelTypeDto;
import org.example.dealership.domain.exception.DomainValidationException;
import org.example.dealership.domain.model.enums.FuelType;

public class FuelTypeMapper {
    public static FuelTypeDto mapToDto(FuelType type) {
        return new FuelTypeDto(type.name());
    }

    public static FuelType mapFromDto(FuelTypeDto dto) {
        try {
            return FuelType.valueOf(dto.name().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DomainValidationException("Unsupported fuel type: " + dto.name());
        }
    }
}
