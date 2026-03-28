package org.dealership.application.port.in.testdrive;

import java.util.UUID;

public interface DeleteTestDriveRequestUseCase {
    Response execute(Request request);

    record Request(UUID id) {}

    record Response() {}
}
