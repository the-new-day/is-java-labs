package org.dealership.application.service.inventory;

import org.dealership.application.mapper.CarMapper;
import org.dealership.application.port.in.inventory.AddCarUseCase;
import org.dealership.application.port.in.inventory.dto.NewCarDetailsDto;
import org.dealership.application.port.out.persistence.CarModelRepository;
import org.dealership.application.port.out.persistence.CarRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.id.CarModelId;

public class AddCarInteractor implements AddCarUseCase {
    private final CarRepository carRepository;
    private final CarModelRepository carModelRepository;
    private final CarMapper carMapper;

    public AddCarInteractor(
            CarRepository carRepository,
            CarModelRepository carModelRepository,
            CarMapper carMapper
    ) {
        this.carRepository = carRepository;
        this.carModelRepository = carModelRepository;
        this.carMapper = carMapper;
    }

    @Override
    public Response execute(Request request) {
        NewCarDetailsDto dto = request.newCar();
        CarModelId modelId = new CarModelId(dto.configuration().carModel().id());
        CarModel model = carModelRepository.findById(modelId)
                .orElseThrow(() -> new EntityNotFoundException("Car model not found: " + modelId));
        CarId carId = carRepository.nextId();
        Car car = carMapper.toDomain(dto, carId, model, false);
        carRepository.save(car);
        return new Response(carId.value());
    }
}
