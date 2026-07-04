package org.dealership.application.mapper;

import org.dealership.application.port.in.common.dto.ComponentTypeDto;
import org.dealership.domain.exception.DomainValidationException;
import org.dealership.domain.model.enums.ComponentType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComponentTypeMapper {

    @Mapping(target = "name", source = "type")
    ComponentTypeDto toDto(ComponentType type);

    default ComponentType toDomain(ComponentTypeDto dto) {
        try {
            return ComponentType.valueOf(dto.name().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DomainValidationException("Unsupported component type: " + dto.name());
        }
    }
}
