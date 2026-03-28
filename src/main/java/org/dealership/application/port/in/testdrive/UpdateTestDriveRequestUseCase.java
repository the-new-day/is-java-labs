package org.dealership.application.port.in.testdrive;

import org.dealership.application.port.in.testdrive.dto.TestDriveRequestDto;

public interface UpdateTestDriveRequestUseCase {
    Response execute(Request request);

    record Request(TestDriveRequestDto testDriveRequest) {}

    record Response() {}
}
