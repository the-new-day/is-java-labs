package org.example.dealership.application.mapping;

import org.example.dealership.application.port.in.common.dto.TransmissionTypeDto;
import org.example.dealership.domain.exception.DomainValidationException;
import org.example.dealership.domain.model.enums.TransmissionType;

public class TransmissionTypeMapper {
    public static TransmissionTypeDto mapToDto(TransmissionType type) {
        return new TransmissionTypeDto(type.name());
    }

    public static TransmissionType mapFromDto(TransmissionTypeDto dto) {
        try {
            return TransmissionType.valueOf(dto.name().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DomainValidationException("Unsupported transmission type: " + dto.name());
        }
    }
}
