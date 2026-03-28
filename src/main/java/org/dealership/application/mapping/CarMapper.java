package org.dealership.application.mapping;

import org.dealership.application.port.in.carcatalog.dto.CarSummaryDto;
import org.dealership.application.port.in.common.dto.CarDetailsDto;
import org.dealership.application.port.in.inventory.dto.NewCarDetailsDto;
import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.id.CarId;

public class CarMapper {
    public static CarDetailsDto mapCarToDetailsDto(Car car) {
        return new CarDetailsDto(
                car.getId().value(),
                ConfigurationMapper.mapToDto(car.getConfiguration()),
                MoneyMapper.mapToDto(car.getPrice()),
                ColorMapper.mapToDto(car.getColor()),
                car.isTestDriveAvailable()
        );
    }

    public static CarSummaryDto mapCarToSummaryDto(Car car) {
        return new CarSummaryDto(
                car.getId().value(),
                CarModelMapper.mapToDto(car.getModel()),
                MoneyMapper.mapToDto(car.getPrice()),
                ColorMapper.mapToDto(car.getColor()),
                car.isTestDriveAvailable()
        );
    }

    public static org.dealership.application.port.in.inventory.dto.CarSummaryDto
    mapCarToInventorySummaryDto(Car car) {
        return new org.dealership.application.port.in.inventory.dto.CarSummaryDto(
                car.getId().value(),
                CarModelMapper.mapToDto(car.getModel()),
                MoneyMapper.mapToDto(car.getPrice()),
                ColorMapper.mapToDto(car.getColor())
        );
    }

    public static Car mapFromNewDto(
            NewCarDetailsDto dto,
            CarId id,
            CarModel model,
            boolean testDriveAvailable
    ) {
        return new Car(
                id,
                VinNumberMapper.mapFromDto(dto.vinNumber()),
                ConfigurationMapper.mapFromDto(dto.configuration(), model),
                ColorMapper.mapFromDto(dto.colorDto()),
                testDriveAvailable
        );
    }
}
