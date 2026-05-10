package org.dealership.application.port.in.assembly;

import org.dealership.application.port.in.assembly.dto.NewAssemblyOrderDto;

import java.util.UUID;

public interface CreateAssemblyOrderUseCase {
    Response execute(Request request);

    record Request(NewAssemblyOrderDto newAssemblyOrder) {}

    record Response(UUID id) {}
}
