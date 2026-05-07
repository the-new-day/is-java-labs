package org.dealership.application.mapper;

import org.dealership.application.port.in.common.dto.CarBodyTypeDto;
import org.dealership.domain.exception.DomainValidationException;
import org.dealership.domain.model.enums.CarBodyType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarBodyTypeMapper {

    CarBodyTypeDto toDto(CarBodyType type);

    default CarBodyType toDomain(CarBodyTypeDto dto) {
        try {
            return CarBodyType.valueOf(dto.name().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DomainValidationException("Unsupported car body type: " + dto.name());
        }
    }
}
