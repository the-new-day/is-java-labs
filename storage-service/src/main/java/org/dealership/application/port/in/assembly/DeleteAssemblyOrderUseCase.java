package org.dealership.application.port.in.assembly;

import java.util.UUID;

public interface DeleteAssemblyOrderUseCase {
    Response execute(Request request);

    record Request(UUID id) {}

    record Response() {}
}
