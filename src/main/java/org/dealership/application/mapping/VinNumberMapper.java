package org.dealership.application.mapping;

import org.dealership.application.port.in.common.dto.VinNumberDto;
import org.dealership.domain.model.vo.VinNumber;

public class VinNumberMapper {
    public static VinNumberDto mapToDto(VinNumber vinNumber) {
        return new VinNumberDto(vinNumber.getValue());
    }

    public static VinNumber mapFromDto(VinNumberDto dto) {
        return new VinNumber(dto.value());
    }
}
