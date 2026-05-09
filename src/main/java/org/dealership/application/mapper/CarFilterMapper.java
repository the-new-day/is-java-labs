package org.dealership.application.mapper;

import org.dealership.application.port.in.carcatalog.dto.CarFilterDto;
import org.dealership.domain.model.carfilter.CarFilter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

@Mapper(componentModel = "spring",
        uses = {
            BaseIdMapper.class,
            MoneyMapper.class,
            CarBodyTypeMapper.class,
            ColorMapper.class,
            DriveTypeMapper.class,
            FuelTypeMapper.class,
            TransmissionTypeMapper.class
        },
        nullValueMappingStrategy = RETURN_NULL,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface CarFilterMapper {

    @Mapping(target = "brand", source = "brandId", qualifiedByName = "toBrandId")
    @Mapping(target = "model", source = "modelId", qualifiedByName = "toCarModelId")
    CarFilter toDomain(CarFilterDto dto);
}