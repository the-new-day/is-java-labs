package org.example.dealership.application.service.testdrive;

import org.example.dealership.application.mapping.TestDriveRequestMapper;
import org.example.dealership.application.port.in.testdrive.GetTestDriveRequestUseCase;
import org.example.dealership.application.port.out.persistence.TestDriveRequestRepository;
import org.example.dealership.domain.exception.EntityNotFoundException;
import org.example.dealership.domain.model.id.TestDriveRequestId;

public class GetTestDriveRequestInteractor implements GetTestDriveRequestUseCase {
    private final TestDriveRequestRepository testDriveRequestRepository;

    public GetTestDriveRequestInteractor(TestDriveRequestRepository testDriveRequestRepository) {
        this.testDriveRequestRepository = testDriveRequestRepository;
    }

    @Override
    public Response execute(Request request) {
        TestDriveRequestId requestId = new TestDriveRequestId(request.id());
        return testDriveRequestRepository.findById(requestId)
                .map(TestDriveRequestMapper::mapToDto)
                .map(Response::new)
                .orElseThrow(() -> new EntityNotFoundException("Test drive request not found: " + requestId));
    }
}
