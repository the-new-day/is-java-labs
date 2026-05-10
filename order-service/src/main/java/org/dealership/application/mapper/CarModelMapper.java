package org.dealership.application.mapper;

import org.dealership.application.port.in.carcatalog.dto.ModelSummaryDto;
import org.dealership.application.port.in.common.dto.CarModelDto;
import org.dealership.domain.model.car.CarModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {
        BaseIdMapper.class,
        BrandMapper.class,
        MoneyMapper.class,
        CarBodyTypeMapper.class,
        FuelTypeMapper.class,
        DriveTypeMapper.class,
        TransmissionTypeMapper.class,
        ComponentTypeMapper.class,
        ComponentVariantSelectionMapper.class
})
public abstract class CarModelMapper {

    @Autowired
    protected MoneyMapper moneyMapper;

    @Autowired
    protected CarBodyTypeMapper carBodyTypeMapper;

    @Autowired
    protected FuelTypeMapper fuelTypeMapper;

    @Autowired
    protected DriveTypeMapper driveTypeMapper;

    @Autowired
    protected TransmissionTypeMapper transmissionTypeMapper;

    @Autowired
    protected ComponentVariantSelectionMapper componentVariantSelectionMapper;

    @Autowired
    protected ComponentTypeMapper componentTypeMapper;

    @Mapping(target = "id", source = "id.value")
    public abstract CarModelDto toDto(CarModel model);

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "name", source = "modelName")
    public abstract ModelSummaryDto toSummaryDto(CarModel model);

    @Mapping(target = "id", source = "id", qualifiedByName = "toCarModelId")
    @Mapping(target = "componentTypes", ignore = true)
    @Mapping(target = "fixedComponentTypes", ignore = true)
    public abstract CarModel toDomain(CarModelDto dto);
}
