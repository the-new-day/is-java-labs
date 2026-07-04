package org.dealership.application.port.in.assembly;

import org.dealership.application.port.in.assembly.dto.AssemblyOrderSummaryDto;

import java.util.List;

public interface ListAssemblyOrdersUseCase {
    Response execute(Request request);

    record Request() {}

    record Response(List<AssemblyOrderSummaryDto> assemblyOrders) {}
}
