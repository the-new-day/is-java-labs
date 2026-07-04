package org.dealership.application.port.in.assembly;

import org.dealership.application.port.in.assembly.dto.NewAssemblyOrderDto;

import java.util.UUID;

public interface UpdateAssemblyOrderUseCase {
    Response execute(Request request);

    record Request(UUID id, NewAssemblyOrderDto assemblyOrder) {}

    record Response() {}
}
