package org.example.dealership.application.mapping;

import org.example.dealership.application.port.in.common.dto.DriveTypeDto;
import org.example.dealership.domain.exception.DomainValidationException;
import org.example.dealership.domain.model.enums.DriveType;

public class DriveTypeMapper {
    public static DriveTypeDto mapToDto(DriveType type) {
        return new DriveTypeDto(type.name());
    }

    public static DriveType mapFromDto(DriveTypeDto dto) {
        try {
            return DriveType.valueOf(dto.name().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DomainValidationException("Unsupported drive type: " + dto.name());
        }
    }
}
