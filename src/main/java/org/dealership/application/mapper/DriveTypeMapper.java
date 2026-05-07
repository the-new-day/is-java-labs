package org.dealership.application.mapper;

import org.dealership.application.port.in.common.dto.DriveTypeDto;
import org.dealership.domain.exception.DomainValidationException;
import org.dealership.domain.model.enums.DriveType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DriveTypeMapper {

    DriveTypeDto toDto(DriveType type);

    default DriveType toDomain(DriveTypeDto dto) {
        try {
            return DriveType.valueOf(dto.name().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DomainValidationException("Unsupported drive type: " + dto.name());
        }
    }
}
