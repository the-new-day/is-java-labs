package org.dealership.application.mapper;

import org.dealership.application.port.in.common.dto.TransmissionTypeDto;
import org.dealership.domain.exception.DomainValidationException;
import org.dealership.domain.model.enums.TransmissionType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransmissionTypeMapper {

    TransmissionTypeDto toDto(TransmissionType type);

    default TransmissionType toDomain(TransmissionTypeDto dto) {
        try {
            return TransmissionType.valueOf(dto.name().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DomainValidationException("Unsupported transmission type: " + dto.name());
        }
    }
}
