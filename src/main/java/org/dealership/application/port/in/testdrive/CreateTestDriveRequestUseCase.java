package org.dealership.application.port.in.testdrive;

import java.time.LocalDateTime;
import java.util.UUID;

public interface CreateTestDriveRequestUseCase {
    Response execute(Request request);

    record Request(UUID clientId, UUID carId, LocalDateTime startsAt) {}

    record Response(UUID id) {}
}
