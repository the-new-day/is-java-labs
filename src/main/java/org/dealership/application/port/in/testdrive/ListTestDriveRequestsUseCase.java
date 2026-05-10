package org.dealership.application.port.in.testdrive;

import org.dealership.application.port.in.testdrive.dto.TestDriveRequestDto;

import java.util.List;
import java.util.UUID;

public interface ListTestDriveRequestsUseCase {
    Response execute(Request request);

    record Request(UUID clientIdFilter) {
        public Request() {
            this(null);
        }
    }

    record Response(List<TestDriveRequestDto> testDriveRequest) {}
}
