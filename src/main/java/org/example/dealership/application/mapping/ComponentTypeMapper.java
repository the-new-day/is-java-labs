package org.example.dealership.application.mapping;

import org.example.dealership.application.port.in.common.dto.ComponentTypeDto;
import org.example.dealership.domain.exception.DomainValidationException;
import org.example.dealership.domain.model.enums.ComponentType;

public class ComponentTypeMapper {
    public static ComponentTypeDto mapToDto(ComponentType type) {
        return new ComponentTypeDto(type.name());
    }

    public static ComponentType mapFromDto(ComponentTypeDto dto) {
        try {
            return ComponentType.valueOf(dto.name().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DomainValidationException("Unsupported component type: " + dto.name());
        }
    }
}
