package org.dealership.application.service.inventory;

import org.dealership.application.mapper.ColorMapper;
import org.dealership.application.mapper.ConfigurationMapper;
import org.dealership.application.port.in.common.dto.CarDetailsDto;
import org.dealership.application.port.in.inventory.UpdateCarUseCase;
import org.dealership.application.port.out.persistence.CarModelRepository;
import org.dealership.application.port.out.persistence.CarRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.configuration.Configuration;
import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.id.CarModelId;

public class UpdateCarInteractor implements UpdateCarUseCase {
    private final CarRepository carRepository;
    private final CarModelRepository carModelRepository;
    private final ConfigurationMapper configurationMapper;
    private final ColorMapper colorMapper;

    public UpdateCarInteractor(
            CarRepository carRepository,
            CarModelRepository carModelRepository,
            ConfigurationMapper configurationMapper,
            ColorMapper colorMapper
    ) {
        this.carRepository = carRepository;
        this.carModelRepository = carModelRepository;
        this.configurationMapper = configurationMapper;
        this.colorMapper = colorMapper;
    }

    @Override
    public Response execute(Request request) {
        CarDetailsDto dto = request.model();
        CarId carId = new CarId(dto.id());
        Car existing = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found: " + carId));

        CarModelId modelId = new CarModelId(dto.configuration().carModel().id());
        carModelRepository.findById(modelId)
                .orElseThrow(() -> new EntityNotFoundException("Car model not found: " + modelId));
        Configuration configuration = configurationMapper.toDomain(dto.configuration());

        Car updated = new Car(
                carId,
                existing.getVinNumber(),
                configuration,
                colorMapper.toDomain(dto.color()),
                dto.testDriveAvailable()
        );
        carRepository.save(updated);
        return new Response();
    }
}
