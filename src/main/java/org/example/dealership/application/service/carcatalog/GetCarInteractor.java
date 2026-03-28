package org.example.dealership.application.service.carcatalog;

import org.example.dealership.application.mapping.CarMapper;
import org.example.dealership.application.port.in.inventory.GetCarUseCase;
import org.example.dealership.application.port.out.persistence.CarRepository;
import org.example.dealership.domain.exception.EntityNotFoundException;
import org.example.dealership.domain.model.car.Car;
import org.example.dealership.domain.model.id.CarId;

public class GetCarInteractor implements GetCarUseCase {
    private final CarRepository carRepository;

    public GetCarInteractor(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Response execute(Request request) {
        CarId carId = new CarId(request.carId());
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found: " + carId));
        return new Response(CarMapper.mapCarToDetailsDto(car));
    }
}
