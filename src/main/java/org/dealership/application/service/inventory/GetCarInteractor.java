package org.dealership.application.service.inventory;

import org.dealership.application.mapping.CarMapper;
import org.dealership.application.port.in.inventory.GetCarUseCase;
import org.dealership.application.port.out.persistence.CarRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.CarId;

public class GetCarInteractor implements GetCarUseCase {
    private final CarRepository carRepository;

    public GetCarInteractor(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Response execute(Request request) {
        CarId carId = new CarId(request.carId());
        return carRepository.findById(carId)
                .map(CarMapper::mapCarToDetailsDto)
                .map(Response::new)
                .orElseThrow(() -> new EntityNotFoundException("Car not found: " + carId));
    }
}
