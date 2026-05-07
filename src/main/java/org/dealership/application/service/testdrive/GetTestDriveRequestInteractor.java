package org.dealership.application.service.testdrive;

import org.dealership.application.mapper.TestDriveRequestMapper;
import org.dealership.application.port.in.testdrive.GetTestDriveRequestUseCase;
import org.dealership.application.port.out.persistence.TestDriveRequestRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.TestDriveRequestId;

public class GetTestDriveRequestInteractor implements GetTestDriveRequestUseCase {
    private final TestDriveRequestRepository testDriveRequestRepository;
    private final TestDriveRequestMapper testDriveRequestMapper;

    public GetTestDriveRequestInteractor(TestDriveRequestRepository testDriveRequestRepository, TestDriveRequestMapper testDriveRequestMapper) {
        this.testDriveRequestRepository = testDriveRequestRepository;
        this.testDriveRequestMapper = testDriveRequestMapper;
    }

    @Override
    public Response execute(Request request) {
        TestDriveRequestId requestId = new TestDriveRequestId(request.id());
        return testDriveRequestRepository.findById(requestId)
                .map(testDriveRequestMapper::toDto)
                .map(Response::new)
                .orElseThrow(() -> new EntityNotFoundException("Test drive request not found: " + requestId));
    }
}
