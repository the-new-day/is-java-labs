package org.dealership.application.mapper;

import org.dealership.application.port.in.common.dto.ComponentTypeDto;
import org.dealership.domain.model.enums.ComponentType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ComponentTypeMapper {
    ComponentTypeDto toDto(ComponentType type);
    ComponentType toDomain(ComponentTypeDto dto);
}
