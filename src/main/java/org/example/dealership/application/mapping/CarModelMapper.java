package org.example.dealership.application.mapping;

import org.example.dealership.application.port.in.carcatalog.dto.ModelSummaryDto;
import org.example.dealership.application.port.in.common.dto.CarModelDto;
import org.example.dealership.application.port.in.common.dto.ComponentTypeDto;
import org.example.dealership.domain.model.car.CarModel;
import org.example.dealership.domain.model.enums.ComponentType;
import org.example.dealership.domain.model.id.CarModelId;

import java.util.Set;
import java.util.stream.Collectors;

public class CarModelMapper {
    public static CarModelDto mapToDto(CarModel model) {
        return new CarModelDto(
                model.getId().value(),
                BrandMapper.mapToDto(model.getBrand()),
                model.getModelName(),
                MoneyMapper.mapToDto(model.getBasePrice()),
                CarBodyTypeMapper.mapToDto(model.getCarBodyType()),
                FuelTypeMapper.mapToDto(model.getFuelType()),
                DriveTypeMapper.mapToDto(model.getDriveType()),
                model.getEngineVolume(),
                model.getEnginePower(),
                TransmissionTypeMapper.mapToDto(model.getBaseTransmissionType()),
                ComponentVariantSelectionMapper.mapToDto(model.getBaseComponentSelection()),
                mapComponentTypesToDto(model.getConfigurableComponentTypes())
        );
    }

    public static CarModel mapFromDto(CarModelDto dto) {
        return new CarModel(
                new CarModelId(dto.id()),
                BrandMapper.mapFromDto(dto.brand()),
                dto.modelName(),
                MoneyMapper.mapFromDto(dto.basePrice()),
                CarBodyTypeMapper.mapFromDto(dto.carBodyType()),
                FuelTypeMapper.mapFromDto(dto.fuelType()),
                DriveTypeMapper.mapFromDto(dto.driveType()),
                dto.engineVolume(),
                dto.enginePower(),
                TransmissionTypeMapper.mapFromDto(dto.baseTransmissionType()),
                ComponentVariantSelectionMapper.mapFromDto(dto.baseComponentSelection()),
                mapComponentTypesFromDto(dto.configurableComponentTypes())
        );
    }

    public static ModelSummaryDto mapToSummaryDto(CarModel model) {
        return new ModelSummaryDto(
                model.getId().value(),
                BrandMapper.mapToDto(model.getBrand()),
                model.getModelName(),
                MoneyMapper.mapToDto(model.getBasePrice())
        );
    }

    private static Set<ComponentTypeDto> mapComponentTypesToDto(Set<ComponentType> types) {
        return types.stream()
                .map(ComponentTypeMapper::mapToDto)
                .collect(Collectors.toUnmodifiableSet());
    }

    private static Set<ComponentType> mapComponentTypesFromDto(Set<ComponentTypeDto> types) {
        return types.stream()
                .map(ComponentTypeMapper::mapFromDto)
                .collect(Collectors.toUnmodifiableSet());
    }
}
