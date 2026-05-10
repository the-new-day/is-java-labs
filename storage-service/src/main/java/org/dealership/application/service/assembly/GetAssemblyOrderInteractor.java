package org.dealership.application.service.assembly;

import org.dealership.application.mapper.AssemblyOrderMapper;
import org.dealership.application.port.in.assembly.GetAssemblyOrderUseCase;
import org.dealership.application.port.out.persistence.AssemblyOrderRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.AssemblyOrderId;

public class GetAssemblyOrderInteractor implements GetAssemblyOrderUseCase {
    private final AssemblyOrderRepository repository;
    private final AssemblyOrderMapper mapper;

    public GetAssemblyOrderInteractor(AssemblyOrderRepository repository, AssemblyOrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Response execute(Request request) {
        AssemblyOrderId id = new AssemblyOrderId(request.id());
        return repository.findById(id)
                .map(mapper::toSummaryDto)
                .map(Response::new)
                .orElseThrow(() -> new EntityNotFoundException("Assembly order not found: " + id));
    }
}
