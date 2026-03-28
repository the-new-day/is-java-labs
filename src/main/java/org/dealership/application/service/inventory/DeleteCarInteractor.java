package org.dealership.application.service.inventory;

import org.dealership.application.port.in.inventory.DeleteCarUseCase;
import org.dealership.application.port.out.persistence.CarRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.CarId;

public class DeleteCarInteractor implements DeleteCarUseCase {
    private final CarRepository carRepository;

    public DeleteCarInteractor(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Response execute(Request request) {
        CarId carId = new CarId(request.id());
        if (carRepository.findById(carId).isEmpty()) {
            throw new EntityNotFoundException("Car not found: " + carId);
        }
        carRepository.deleteById(carId);
        return new Response();
    }
}
