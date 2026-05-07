package org.dealership.application.service.inventory;

import org.dealership.application.mapper.CarMapper;
import org.dealership.application.port.in.inventory.GetCarUseCase;
import org.dealership.application.port.out.persistence.CarRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.CarId;

public class GetCarInteractor implements GetCarUseCase {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public GetCarInteractor(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    @Override
    public Response execute(Request request) {
        CarId carId = new CarId(request.carId());
        return carRepository.findById(carId)
                .map(carMapper::toDetailsDto)
                .map(Response::new)
                .orElseThrow(() -> new EntityNotFoundException("Car not found: " + carId));
    }
}
