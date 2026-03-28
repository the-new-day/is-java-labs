package org.dealership.application.mapping;

import org.dealership.application.port.in.common.dto.DriveTypeDto;
import org.dealership.domain.exception.DomainValidationException;
import org.dealership.domain.model.enums.DriveType;

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
