package org.dealership.application.mapping;

import org.dealership.application.port.in.carcatalog.dto.CarFilterDto;
import org.dealership.domain.model.carfilter.CarFilter;
import org.dealership.domain.model.id.BrandId;
import org.dealership.domain.model.id.CarModelId;

public class CarFilterMapper {
    public static CarFilter mapFromDto(CarFilterDto dto) {
        return new CarFilter(
                MoneyMapper.mapFromDto(dto.minPrice()),
                MoneyMapper.mapFromDto(dto.maxPrice()),
                CarBodyTypeMapper.mapFromDto(dto.bodyType()),
                new BrandId(dto.brandId()),
                ColorMapper.mapFromDto(dto.color()),
                new CarModelId(dto.modelId()),
                DriveTypeMapper.mapFromDto(dto.driveType()),
                dto.minEnginePower(),
                dto.maxEnginePower(),
                dto.minEngineVolume(),
                dto.maxEngineVolume(),
                FuelTypeMapper.mapFromDto(dto.fuelType())
        );
    }
}
