package org.dealership.application.mapper;

import org.dealership.application.port.in.common.dto.CarBodyTypeDto;
import org.dealership.domain.model.enums.CarBodyType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarBodyTypeMapper {
    CarBodyTypeDto toDto(CarBodyType type);
    CarBodyType toDomain(CarBodyTypeDto dto);
}
