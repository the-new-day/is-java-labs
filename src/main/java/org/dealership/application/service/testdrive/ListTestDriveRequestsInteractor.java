package org.dealership.application.service.testdrive;

import org.dealership.application.mapper.TestDriveRequestMapper;
import org.dealership.application.port.in.testdrive.ListTestDriveRequestsUseCase;
import org.dealership.application.port.out.persistence.TestDriveRequestRepository;

public class ListTestDriveRequestsInteractor implements ListTestDriveRequestsUseCase {
    private final TestDriveRequestRepository testDriveRequestRepository;
    private final TestDriveRequestMapper testDriveRequestMapper;

    public ListTestDriveRequestsInteractor(TestDriveRequestRepository testDriveRequestRepository, TestDriveRequestMapper testDriveRequestMapper) {
        this.testDriveRequestRepository = testDriveRequestRepository;
        this.testDriveRequestMapper = testDriveRequestMapper;
    }

    @Override
    public Response execute(Request request) {
        return new Response(
                testDriveRequestRepository.findAll().stream()
                        .map(testDriveRequestMapper::toDto)
                        .toList()
        );
    }
}
