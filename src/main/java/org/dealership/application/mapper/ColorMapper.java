package org.dealership.application.mapper;

import org.dealership.application.port.in.common.dto.ColorDto;
import org.dealership.domain.exception.DomainValidationException;
import org.dealership.domain.model.enums.Color;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ColorMapper {

    ColorDto toDto(Color color);

    default Color toDomain(ColorDto dto) {
        try {
            return Color.valueOf(dto.name().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DomainValidationException("Unsupported color: " + dto.name());
        }
    }
}
