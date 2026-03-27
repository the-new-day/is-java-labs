package org.example.dealership.application.port.in.testdrive;

import java.util.UUID;

public interface SetTestDriveAvailableUseCase {
    Response execute(Request request);

    record Request(UUID carId, boolean isAvailable) {}

    record Response() {}
}
