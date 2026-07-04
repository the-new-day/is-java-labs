package org.dealership.application.port.in.testdrive;

import org.dealership.application.port.in.testdrive.dto.NewTestDriveRequestDto;

import java.util.UUID;

public interface UpdateTestDriveRequestUseCase {
    Response execute(Request request);

    record Request(UUID requestId, NewTestDriveRequestDto testDriveRequest) {}

    record Response() {}
}
