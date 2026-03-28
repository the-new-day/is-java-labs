package org.example.dealership.application.service.testdrive;

import org.example.dealership.application.port.in.testdrive.CreateTestDriveRequestUseCase;
import org.example.dealership.application.port.out.persistence.CarRepository;
import org.example.dealership.application.port.out.persistence.TestDriveRequestRepository;
import org.example.dealership.domain.exception.DomainValidationException;
import org.example.dealership.domain.exception.EntityNotFoundException;
import org.example.dealership.domain.model.car.Car;
import org.example.dealership.domain.model.id.CarId;
import org.example.dealership.domain.model.id.UserId;
import org.example.dealership.domain.model.testdrive.TestDriveRequest;

public class CreateTestDriveRequestInteractor implements CreateTestDriveRequestUseCase {
    private final TestDriveRequestRepository testDriveRequestRepository;
    private final CarRepository carRepository;

    public CreateTestDriveRequestInteractor(
            TestDriveRequestRepository testDriveRequestRepository,
            CarRepository carRepository
    ) {
        this.testDriveRequestRepository = testDriveRequestRepository;
        this.carRepository = carRepository;
    }

    @Override
    public Response execute(Request request) {
        CarId carId = new CarId(request.carId());
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found: " + carId));
        if (!car.isTestDriveAvailable()) {
            throw new DomainValidationException("Car is not available for test drive.");
        }
        TestDriveRequest testDriveRequest = new TestDriveRequest(
                testDriveRequestRepository.nextId(),
                new UserId(request.clientId()),
                carId,
                request.startsAt()
        );
        testDriveRequestRepository.save(testDriveRequest);
        return new Response(testDriveRequest.getId().value());
    }
}
