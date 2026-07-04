package org.dealership.application.mapper;

import org.dealership.application.port.in.carcatalog.dto.CarSummaryDto;
import org.dealership.application.port.in.common.dto.CarDetailsDto;
import org.dealership.domain.model.car.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {
        ConfigurationMapper.class,
        ColorMapper.class,
        VinNumberMapper.class,
        CarModelMapper.class,
        MoneyMapper.class
})
public abstract class CarMapper {

    @Autowired
    protected VinNumberMapper vinNumberMapper;

    @Autowired
    protected ConfigurationMapper configurationMapper;

    @Autowired
    protected ColorMapper colorMapper;

    @Mapping(target = "id", source = "id.value")
    public abstract CarDetailsDto toDetailsDto(Car car);

    @Mapping(target = "id", source = "id.value")
    public abstract CarSummaryDto toSummaryDto(Car car);
}
