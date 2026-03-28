package org.example.dealership.application.service.inventory;

import org.example.dealership.application.mapping.CarMapper;
import org.example.dealership.application.port.in.inventory.AddCarUseCase;
import org.example.dealership.application.port.in.inventory.dto.NewCarDetailsDto;
import org.example.dealership.application.port.out.persistence.CarModelRepository;
import org.example.dealership.application.port.out.persistence.CarRepository;
import org.example.dealership.domain.exception.EntityNotFoundException;
import org.example.dealership.domain.model.car.Car;
import org.example.dealership.domain.model.car.CarModel;
import org.example.dealership.domain.model.id.CarId;
import org.example.dealership.domain.model.id.CarModelId;

public class AddCarInteractor implements AddCarUseCase {
    private final CarRepository carRepository;
    private final CarModelRepository carModelRepository;

    public AddCarInteractor(
            CarRepository carRepository,
            CarModelRepository carModelRepository
    ) {
        this.carRepository = carRepository;
        this.carModelRepository = carModelRepository;
    }

    @Override
    public Response execute(Request request) {
        NewCarDetailsDto dto = request.newCar();
        CarModelId modelId = new CarModelId(dto.configuration().carModel().id());
        CarModel model = carModelRepository.findById(modelId)
                .orElseThrow(() -> new EntityNotFoundException("Car model not found: " + modelId));
        CarId carId = carRepository.nextId();
        Car car = CarMapper.mapFromNewDto(dto, carId, model);
        carRepository.save(car);
        return new Response(carId.value());
    }
}
