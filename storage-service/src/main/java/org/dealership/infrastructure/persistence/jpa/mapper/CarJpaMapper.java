package org.dealership.infrastructure.persistence.jpa.mapper;

import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.configuration.Configuration;
import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.vo.VinNumber;
import org.dealership.infrastructure.persistence.jpa.entity.CarJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.ConfigurationJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class CarJpaMapper {

    private final ConfigurationJpaMapper configurationMapper;

    public CarJpaMapper(ConfigurationJpaMapper configurationMapper) {
        this.configurationMapper = configurationMapper;
    }

    public Car toDomain(CarJpaEntity entity) {
        Configuration configuration = configurationMapper.toDomain(entity.getConfiguration());
        return new Car(
                new CarId(entity.getId()),
                new VinNumber(entity.getVin()),
                configuration,
                entity.getColor(),
                entity.isTestDriveAvailable()
        );
    }

    public CarJpaEntity toEntity(Car car, ConfigurationJpaEntity configurationEntity) {
        return new CarJpaEntity(
                car.getId().value(),
                car.getVinNumber().getValue(),
                configurationEntity,
                car.getColor(),
                car.isTestDriveAvailable()
        );
    }

    public void updateEntity(CarJpaEntity entity, Car car, ConfigurationJpaEntity configurationEntity) {
        entity.setVin(car.getVinNumber().getValue());
        entity.setConfiguration(configurationEntity);
        entity.setColor(car.getColor());
        entity.setTestDriveAvailable(car.isTestDriveAvailable());
    }
}
