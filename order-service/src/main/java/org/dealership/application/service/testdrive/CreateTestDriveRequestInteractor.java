package org.dealership.application.service.testdrive;

import org.dealership.application.port.in.testdrive.CreateTestDriveRequestUseCase;
import org.dealership.application.port.out.persistence.CarRepository;
import org.dealership.application.port.out.persistence.TestDriveRequestRepository;
import org.dealership.domain.exception.DomainValidationException;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.testdrive.TestDriveRequest;

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
        CarId carId = new CarId(request.request().carId());
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found: " + carId));
        if (!car.isTestDriveAvailable()) {
            throw new DomainValidationException("Car is not available for test drive.");
        }
        TestDriveRequest testDriveRequest = new TestDriveRequest(
                testDriveRequestRepository.nextId(),
                new UserId(request.request().clientId()),
                carId,
                request.request().startsAt()
        );
        testDriveRequestRepository.save(testDriveRequest);
        return new Response(testDriveRequest.getId().value());
    }
}
