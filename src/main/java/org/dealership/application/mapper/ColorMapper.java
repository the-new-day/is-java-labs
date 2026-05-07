package org.dealership.application.mapper;

import org.dealership.application.port.in.common.dto.ColorDto;
import org.dealership.domain.model.enums.Color;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ColorMapper {
    ColorDto toDto(Color color);
    Color toDomain(ColorDto dto);
}
