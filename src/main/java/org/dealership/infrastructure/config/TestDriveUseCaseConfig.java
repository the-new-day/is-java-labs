package org.dealership.infrastructure.config;

import org.dealership.application.mapper.TestDriveRequestMapper;
import org.dealership.application.port.in.testdrive.CreateTestDriveRequestUseCase;
import org.dealership.application.port.in.testdrive.DeleteTestDriveRequestUseCase;
import org.dealership.application.port.in.testdrive.GetTestDriveRequestUseCase;
import org.dealership.application.port.in.testdrive.ListTestDriveRequestsUseCase;
import org.dealership.application.port.in.testdrive.SetTestDriveAvailableUseCase;
import org.dealership.application.port.in.testdrive.UpdateTestDriveRequestUseCase;
import org.dealership.application.port.out.persistence.CarRepository;
import org.dealership.application.port.out.persistence.TestDriveRequestRepository;
import org.dealership.application.service.testdrive.CreateTestDriveRequestInteractor;
import org.dealership.application.service.testdrive.DeleteTestDriveRequestInteractor;
import org.dealership.application.service.testdrive.GetTestDriveRequestInteractor;
import org.dealership.application.service.testdrive.ListTestDriveRequestsInteractor;
import org.dealership.application.service.testdrive.SetTestDriveAvailableInteractor;
import org.dealership.application.service.testdrive.UpdateTestDriveRequestInteractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestDriveUseCaseConfig {

    @Bean
    public CreateTestDriveRequestUseCase createTestDriveRequestUseCase(
            TestDriveRequestRepository testDriveRequestRepository,
            CarRepository carRepository
    ) {
        return new CreateTestDriveRequestInteractor(testDriveRequestRepository, carRepository);
    }

    @Bean
    public DeleteTestDriveRequestUseCase deleteTestDriveRequestUseCase(
            TestDriveRequestRepository testDriveRequestRepository
    ) {
        return new DeleteTestDriveRequestInteractor(testDriveRequestRepository);
    }

    @Bean
    public GetTestDriveRequestUseCase getTestDriveRequestUseCase(
            TestDriveRequestRepository testDriveRequestRepository,
            TestDriveRequestMapper testDriveRequestMapper
    ) {
        return new GetTestDriveRequestInteractor(testDriveRequestRepository, testDriveRequestMapper);
    }

    @Bean
    public ListTestDriveRequestsUseCase listTestDriveRequestsUseCase(
            TestDriveRequestRepository testDriveRequestRepository,
            TestDriveRequestMapper testDriveRequestMapper
    ) {
        return new ListTestDriveRequestsInteractor(testDriveRequestRepository, testDriveRequestMapper);
    }

    @Bean
    public SetTestDriveAvailableUseCase setTestDriveAvailableUseCase(
            CarRepository carRepository
    ) {
        return new SetTestDriveAvailableInteractor(carRepository);
    }

    @Bean
    public UpdateTestDriveRequestUseCase updateTestDriveRequestUseCase(
            TestDriveRequestRepository testDriveRequestRepository,
            TestDriveRequestMapper testDriveRequestMapper
    ) {
        return new UpdateTestDriveRequestInteractor(testDriveRequestRepository, testDriveRequestMapper);
    }
}
