package org.dealership.infrastructure.config;

import org.dealership.application.mapper.BaseIdMapper;
import org.dealership.application.mapper.TestDriveRequestMapper;
import org.dealership.application.port.in.testdrive.*;
import org.dealership.application.port.out.persistence.CarRepository;
import org.dealership.application.port.out.persistence.TestDriveRequestRepository;
import org.dealership.application.service.testdrive.*;
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
    public ListClientTestDriveRequestsUseCase listClientTestDriveRequestsUseCase(
            TestDriveRequestRepository testDriveRequestRepository,
            TestDriveRequestMapper testDriveRequestMapper,
            BaseIdMapper idMapper
    ) {
        return new ListClientTestDriveRequestsInteractor(
                testDriveRequestRepository, testDriveRequestMapper, idMapper);
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
