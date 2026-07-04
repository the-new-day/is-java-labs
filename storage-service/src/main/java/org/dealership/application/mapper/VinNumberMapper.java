package org.dealership.application.mapper;

import org.dealership.application.port.in.common.dto.VinNumberDto;
import org.dealership.domain.model.vo.VinNumber;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VinNumberMapper {
    VinNumberDto toDto(VinNumber vinNumber);
    VinNumber toDomain(VinNumberDto dto);
}
