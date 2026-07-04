package org.dealership.application.service.assembly;

import org.dealership.application.mapper.AssemblyOrderMapper;
import org.dealership.application.port.in.assembly.UpdateAssemblyOrderUseCase;
import org.dealership.application.port.out.persistence.AssemblyOrderRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.assembly.AssemblyOrder;
import org.dealership.domain.model.id.AssemblyOrderId;

public class UpdateAssemblyOrderInteractor implements UpdateAssemblyOrderUseCase {
    private final AssemblyOrderRepository repository;
    private final AssemblyOrderMapper mapper;

    public UpdateAssemblyOrderInteractor(AssemblyOrderRepository repository, AssemblyOrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Response execute(Request request) {
        AssemblyOrderId id = new AssemblyOrderId(request.id());
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Assembly order not found: " + id));
        AssemblyOrder updated = mapper.toDomain(id, request.assemblyOrder());
        repository.save(updated);
        return new Response();
    }
}
