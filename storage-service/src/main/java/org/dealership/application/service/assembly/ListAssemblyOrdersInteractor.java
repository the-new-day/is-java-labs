package org.dealership.application.service.assembly;

import org.dealership.application.mapper.AssemblyOrderMapper;
import org.dealership.application.port.in.assembly.ListAssemblyOrdersUseCase;
import org.dealership.application.port.out.persistence.AssemblyOrderRepository;

public class ListAssemblyOrdersInteractor implements ListAssemblyOrdersUseCase {
    private final AssemblyOrderRepository repository;
    private final AssemblyOrderMapper mapper;

    public ListAssemblyOrdersInteractor(AssemblyOrderRepository repository, AssemblyOrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Response execute(Request request) {
        return new Response(
                repository.findAll().stream()
                        .map(mapper::toSummaryDto)
                        .toList()
        );
    }
}
