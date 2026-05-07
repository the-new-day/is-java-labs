package org.dealership.application.mapper;

import org.dealership.application.port.in.carcatalog.dto.ModelSummaryDto;
import org.dealership.application.port.in.common.dto.CarModelDto;
import org.dealership.application.port.in.inventory.dto.NewModelDto;
import org.dealership.domain.model.car.Brand;
import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.id.CarModelId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

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
    public abstract ModelSummaryDto toSummaryDto(CarModel model);

    @Mapping(target = "id", source = "id", qualifiedByName = "toCarModelId")
    public abstract CarModel toDomain(CarModelDto dto);

    public CarModel toDomain(NewModelDto dto, CarModelId id, Brand brand) {
        return new CarModel(
                id,
                brand,
                dto.name(),
                moneyMapper.toDomain(dto.basePrice()),
                carBodyTypeMapper.toDomain(dto.carBodyType()),
                fuelTypeMapper.toDomain(dto.fuelType()),
                driveTypeMapper.toDomain(dto.driveType()),
                dto.engineVolume(),
                dto.enginePower(),
                transmissionTypeMapper.toDomain(dto.baseTransmissionType()),
                componentVariantSelectionMapper.toDomain(dto.baseComponentSelection()),
                dto.configurableComponentTypes().stream()
                        .map(componentTypeMapper::toDomain)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }
}
