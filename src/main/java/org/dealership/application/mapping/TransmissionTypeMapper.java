package org.dealership.application.mapping;

import org.dealership.application.port.in.common.dto.TransmissionTypeDto;
import org.dealership.domain.exception.DomainValidationException;
import org.dealership.domain.model.enums.TransmissionType;

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
