package org.dealership.application.mapper;

import org.dealership.application.port.in.common.dto.DriveTypeDto;
import org.dealership.domain.model.enums.DriveType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DriveTypeMapper {
    DriveTypeDto toDto(DriveType type);
    DriveType toDomain(DriveTypeDto dto);
}
