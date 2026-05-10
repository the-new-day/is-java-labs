package org.dealership.application.port.in.assembly;

import org.dealership.application.port.in.assembly.dto.AssemblyOrderSummaryDto;

import java.util.UUID;

public interface GetAssemblyOrderUseCase {
    Response execute(Request request);

    record Request(UUID id) {}

    record Response(AssemblyOrderSummaryDto assemblyOrder) {}
}
