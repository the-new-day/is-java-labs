package org.dealership.application.mapper;

import org.dealership.application.port.in.common.dto.TransmissionTypeDto;
import org.dealership.domain.model.enums.TransmissionType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransmissionTypeMapper {
    TransmissionTypeDto toDto(TransmissionType type);
    TransmissionType toDomain(TransmissionTypeDto dto);
}
