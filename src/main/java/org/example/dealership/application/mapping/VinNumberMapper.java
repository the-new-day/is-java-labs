package org.example.dealership.application.mapping;

import org.example.dealership.application.port.in.common.dto.VinNumberDto;
import org.example.dealership.domain.model.vo.VinNumber;

public class VinNumberMapper {
    public static VinNumberDto mapToDto(VinNumber vinNumber) {
        return new VinNumberDto(vinNumber.getValue());
    }

    public static VinNumber mapFromDto(VinNumberDto dto) {
        return new VinNumber(dto.value());
    }
}
