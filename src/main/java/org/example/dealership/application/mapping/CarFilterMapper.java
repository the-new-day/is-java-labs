package org.example.dealership.application.mapping;

import org.example.dealership.application.port.in.carcatalog.dto.CarFilterDto;
import org.example.dealership.domain.model.carfilter.CarFilter;
import org.example.dealership.domain.model.id.BrandId;
import org.example.dealership.domain.model.id.CarModelId;

public class CarFilterMapper {
    public static CarFilter mapFromDto(CarFilterDto carFilterDto) {
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
                .build();
    }
}
