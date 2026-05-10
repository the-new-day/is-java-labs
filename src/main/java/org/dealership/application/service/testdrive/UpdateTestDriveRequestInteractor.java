package org.dealership.application.service.testdrive;

import org.dealership.application.mapper.TestDriveRequestMapper;
import org.dealership.application.port.in.testdrive.UpdateTestDriveRequestUseCase;
import org.dealership.application.port.in.testdrive.dto.NewTestDriveRequestDto;
import org.dealership.application.port.in.testdrive.dto.TestDriveRequestDto;
import org.dealership.application.port.out.persistence.TestDriveRequestRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.TestDriveRequestId;

public class UpdateTestDriveRequestInteractor implements UpdateTestDriveRequestUseCase {
    private final TestDriveRequestRepository testDriveRequestRepository;
    private final TestDriveRequestMapper testDriveRequestMapper;

    public UpdateTestDriveRequestInteractor(
            TestDriveRequestRepository testDriveRequestRepository,
            TestDriveRequestMapper testDriveRequestMapper
    ) {
        this.testDriveRequestRepository = testDriveRequestRepository;
        this.testDriveRequestMapper = testDriveRequestMapper;
    }

    @Override
    public Response execute(Request request) {
        NewTestDriveRequestDto dto = request.testDriveRequest();
        TestDriveRequestId requestId = new TestDriveRequestId(request.requestId());
        if (testDriveRequestRepository.findById(requestId).isEmpty()) {
            throw new EntityNotFoundException("Test drive request not found: " + requestId);
        }
        testDriveRequestRepository.save(testDriveRequestMapper.toDomain(requestId, dto));
        return new Response();
    }
}
