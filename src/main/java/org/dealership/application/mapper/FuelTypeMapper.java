package org.dealership.application.mapper;

import org.dealership.application.port.in.common.dto.FuelTypeDto;
import org.dealership.domain.exception.DomainValidationException;
import org.dealership.domain.model.enums.FuelType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FuelTypeMapper {

    FuelTypeDto toDto(FuelType type);

    default FuelType toDomain(FuelTypeDto dto) {
        try {
            return FuelType.valueOf(dto.name().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DomainValidationException("Unsupported fuel type: " + dto.name());
        }
    }
}
