package org.dealership.application.mapper;

import org.dealership.application.port.in.carcatalog.dto.CarFilterDto;
import org.dealership.domain.model.carfilter.CarFilter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        BaseIdMapper.class,
        MoneyMapper.class,
        CarBodyTypeMapper.class,
        ColorMapper.class,
        DriveTypeMapper.class,
        FuelTypeMapper.class
})
public interface CarFilterMapper {

    @Mapping(target = "brand", source = "brandId", qualifiedByName = "toBrandId")
    @Mapping(target = "model", source = "modelId", qualifiedByName = "toCarModelId")
    CarFilter toDomain(CarFilterDto dto);
}