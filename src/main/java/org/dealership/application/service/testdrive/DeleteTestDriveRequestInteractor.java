package org.dealership.application.service.testdrive;

import org.dealership.application.port.in.testdrive.DeleteTestDriveRequestUseCase;
import org.dealership.application.port.out.persistence.TestDriveRequestRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.TestDriveRequestId;

public class DeleteTestDriveRequestInteractor implements DeleteTestDriveRequestUseCase {
    private final TestDriveRequestRepository testDriveRequestRepository;

    public DeleteTestDriveRequestInteractor(TestDriveRequestRepository testDriveRequestRepository) {
        this.testDriveRequestRepository = testDriveRequestRepository;
    }

    @Override
    public Response execute(Request request) {
        TestDriveRequestId requestId = new TestDriveRequestId(request.id());
        if (testDriveRequestRepository.findById(requestId).isEmpty()) {
            throw new EntityNotFoundException("Test drive request not found: " + requestId);
        }
        testDriveRequestRepository.deleteById(requestId);
        return new Response();
    }
}
