package org.example.dealership.application.service.testdrive;

import org.example.dealership.application.port.in.testdrive.SetTestDriveAvailableUseCase;
import org.example.dealership.application.port.out.persistence.CarRepository;
import org.example.dealership.domain.exception.EntityNotFoundException;
import org.example.dealership.domain.model.car.Car;
import org.example.dealership.domain.model.id.CarId;

public class SetTestDriveAvailableInteractor implements SetTestDriveAvailableUseCase {
    private final CarRepository carRepository;

    public SetTestDriveAvailableInteractor(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Response execute(Request request) {
        CarId carId = new CarId(request.carId());
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found: " + carId));
        carRepository.save(car.withTestDriveAvailability(request.isAvailable()));
        return new Response();
    }
}
