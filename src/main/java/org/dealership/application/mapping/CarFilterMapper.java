package org.dealership.application.mapping;

import org.dealership.application.port.in.carcatalog.dto.CarFilterDto;
import org.dealership.domain.common.specification.Specification;
import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.carfilter.CarFilter;
import org.dealership.domain.model.id.BrandId;
import org.dealership.domain.model.id.CarModelId;

public class CarFilterMapper {
    public static Specification<Car> mapSpecFromDto(CarFilterDto carFilterDto) {
        return CarFilter
                .builder()
                .priceRange(
                        MoneyMapper.mapFromDto(carFilterDto.minPrice()),
                        MoneyMapper.mapFromDto(carFilterDto.maxPrice())
                )
                .bodyType(CarBodyTypeMapper.mapFromDto(carFilterDto.bodyType()))
                .brand(new BrandId(carFilterDto.brandId()))
                .color(ColorMapper.mapFromDto(carFilterDto.color()))
                .model(new CarModelId(carFilterDto.modelId()))
                .driveType(DriveTypeMapper.mapFromDto(carFilterDto.driveType()))
                .enginePower(carFilterDto.minEnginePower(), carFilterDto.maxEnginePower())
                .engineVolume(carFilterDto.minEngineVolume(), carFilterDto.maxEngineVolume())
                .fuelType(FuelTypeMapper.mapFromDto(carFilterDto.fuelType()))
                .buildSpec();
    }

    public static CarFilter mapFromDto(CarFilterDto carFilterDto) {
        return new CarFilter(mapSpecFromDto(carFilterDto));
    }
}
