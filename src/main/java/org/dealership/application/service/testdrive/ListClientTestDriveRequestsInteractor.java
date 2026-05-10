package org.dealership.application.service.testdrive;

import org.dealership.application.mapper.BaseIdMapper;
import org.dealership.application.mapper.TestDriveRequestMapper;
import org.dealership.application.port.in.testdrive.ListClientTestDriveRequestsUseCase;
import org.dealership.application.port.out.persistence.TestDriveRequestRepository;
import org.dealership.domain.model.testdrive.TestDriveRequest;

import java.util.List;

public class ListClientTestDriveRequestsInteractor implements ListClientTestDriveRequestsUseCase {
    private final TestDriveRequestRepository testDriveRequestRepository;
    private final TestDriveRequestMapper testDriveRequestMapper;
    private final BaseIdMapper idMapper;

    public ListClientTestDriveRequestsInteractor(
            TestDriveRequestRepository testDriveRequestRepository,
            TestDriveRequestMapper testDriveRequestMapper,
            BaseIdMapper idMapper
    ) {
        this.testDriveRequestRepository = testDriveRequestRepository;
        this.testDriveRequestMapper = testDriveRequestMapper;
        this.idMapper = idMapper;
    }

    @Override
    public Response execute(Request request) {
        List<TestDriveRequest> requests = testDriveRequestRepository
                .findByClientId(idMapper.toUserId(request.clientId()));
        return new Response(requests.stream().map(testDriveRequestMapper::toDto).toList());
    }
}
