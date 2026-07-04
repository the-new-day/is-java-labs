package org.dealership.application.port.in.testdrive;

import org.dealership.application.port.in.testdrive.dto.TestDriveRequestDto;

import java.util.List;
import java.util.UUID;

public interface ListClientTestDriveRequestsUseCase {
    Response execute(Request request);

    record Request(UUID clientId) {}

    record Response(List<TestDriveRequestDto> testDriveRequest) {}
}
