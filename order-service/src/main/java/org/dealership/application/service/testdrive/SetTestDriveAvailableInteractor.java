package org.dealership.application.service.testdrive;

import org.dealership.application.port.in.testdrive.SetTestDriveAvailableUseCase;
import org.dealership.application.port.out.persistence.CarRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.id.CarId;

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
