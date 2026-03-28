package org.example.dealership.application.service.inventory;

import org.example.dealership.application.port.in.inventory.DeleteCarUseCase;
import org.example.dealership.application.port.out.persistence.CarRepository;
import org.example.dealership.domain.exception.EntityNotFoundException;
import org.example.dealership.domain.model.id.CarId;

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
