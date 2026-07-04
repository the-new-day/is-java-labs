package org.dealership.application.mapper;

import org.dealership.application.port.in.common.dto.CarBodyTypeDto;
import org.dealership.domain.exception.DomainValidationException;
import org.dealership.domain.model.enums.CarBodyType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarBodyTypeMapper {

    @Mapping(target = "name", source = "type")
    CarBodyTypeDto toDto(CarBodyType type);

    default CarBodyType toDomain(CarBodyTypeDto dto) {
        try {
            return CarBodyType.valueOf(dto.name().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DomainValidationException("Unsupported car body type: " + dto.name());
        }
    }
}
