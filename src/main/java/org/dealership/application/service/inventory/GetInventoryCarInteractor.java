package org.dealership.application.service.inventory;

import org.dealership.application.mapper.CarMapper;
import org.dealership.application.port.in.inventory.GetInventoryCarUseCase;
import org.dealership.application.port.out.persistence.CarRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.CarId;

public class GetInventoryCarInteractor implements GetInventoryCarUseCase {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public GetInventoryCarInteractor(CarRepository carRepository, CarMapper carMapper) {
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
