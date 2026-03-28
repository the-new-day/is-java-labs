package org.dealership.application.service.testdrive;

import org.dealership.application.mapping.TestDriveRequestMapper;
import org.dealership.application.port.in.testdrive.ListTestDriveRequestsUseCase;
import org.dealership.application.port.out.persistence.TestDriveRequestRepository;

public class ListTestDriveRequestsInteractor implements ListTestDriveRequestsUseCase {
    private final TestDriveRequestRepository testDriveRequestRepository;

    public ListTestDriveRequestsInteractor(TestDriveRequestRepository testDriveRequestRepository) {
        this.testDriveRequestRepository = testDriveRequestRepository;
    }

    @Override
    public Response execute(Request request) {
        return new Response(
                testDriveRequestRepository.findAll().stream()
                        .map(TestDriveRequestMapper::mapToDto)
                        .toList()
        );
    }
}
