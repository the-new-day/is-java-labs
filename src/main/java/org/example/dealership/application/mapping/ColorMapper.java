package org.example.dealership.application.mapping;

import org.example.dealership.application.port.in.common.dto.ColorDto;
import org.example.dealership.domain.exception.DomainValidationException;
import org.example.dealership.domain.model.enums.Color;

public class ColorMapper {
    public static ColorDto mapToDto(Color type) {
        return new ColorDto(type.name());
    }

    public static Color mapFromDto(ColorDto dto) {
        try {
            return Color.valueOf(dto.name().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DomainValidationException("Unsupported color: " + dto.name());
        }
    }
}
