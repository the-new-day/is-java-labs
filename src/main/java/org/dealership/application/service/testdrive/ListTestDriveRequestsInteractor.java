package org.dealership.application.service.testdrive;

import org.dealership.application.mapper.TestDriveRequestMapper;
import org.dealership.application.port.in.testdrive.ListTestDriveRequestsUseCase;
import org.dealership.application.port.out.persistence.TestDriveRequestRepository;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.testdrive.TestDriveRequest;

import java.util.List;

public class ListTestDriveRequestsInteractor implements ListTestDriveRequestsUseCase {
    private final TestDriveRequestRepository testDriveRequestRepository;
    private final TestDriveRequestMapper testDriveRequestMapper;

    public ListTestDriveRequestsInteractor(TestDriveRequestRepository testDriveRequestRepository, TestDriveRequestMapper testDriveRequestMapper) {
        this.testDriveRequestRepository = testDriveRequestRepository;
        this.testDriveRequestMapper = testDriveRequestMapper;
    }

    @Override
    public Response execute(Request request) {
        List<TestDriveRequest> requests = request.clientIdFilter() == null
                ? testDriveRequestRepository.findAll()
                : testDriveRequestRepository.findByClientId(new UserId(request.clientIdFilter()));
        return new Response(requests.stream().map(testDriveRequestMapper::toDto).toList());
    }
}
