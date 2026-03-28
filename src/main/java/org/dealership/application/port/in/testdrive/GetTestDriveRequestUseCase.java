package org.dealership.application.port.in.testdrive;

import org.dealership.application.port.in.testdrive.dto.TestDriveRequestDto;

import java.util.UUID;

public interface GetTestDriveRequestUseCase {
    Response execute(Request request);

    record Request(UUID id) {}

    record Response(TestDriveRequestDto testDriveRequest) {}
}
