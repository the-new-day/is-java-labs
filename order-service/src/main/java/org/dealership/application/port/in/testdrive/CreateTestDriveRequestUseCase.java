package org.dealership.application.port.in.testdrive;

import org.dealership.application.port.in.testdrive.dto.NewTestDriveRequestDto;

import java.time.LocalDateTime;
import java.util.UUID;

public interface CreateTestDriveRequestUseCase {
    Response execute(Request request);

    record Request(NewTestDriveRequestDto request) {}

    record Response(UUID id) {}
}
