package org.dealership.application.mapper;

import org.dealership.application.port.in.common.dto.FuelTypeDto;
import org.dealership.domain.model.enums.FuelType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FuelTypeMapper {
    FuelTypeDto toDto(FuelType type);
    FuelType toDomain(FuelTypeDto dto);
}
