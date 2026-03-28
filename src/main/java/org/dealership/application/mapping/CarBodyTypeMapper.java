package org.dealership.application.mapping;

import org.dealership.application.port.in.common.dto.CarBodyTypeDto;
import org.dealership.domain.exception.DomainValidationException;
import org.dealership.domain.model.enums.CarBodyType;

public class CarBodyTypeMapper {
    public static CarBodyTypeDto mapToDto(CarBodyType type) {
        return new CarBodyTypeDto(type.name());
    }

    public static CarBodyType mapFromDto(CarBodyTypeDto dto) {
        try {
            return CarBodyType.valueOf(dto.name().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DomainValidationException("Unsupported car body type: " + dto.name());
        }
    }
}
