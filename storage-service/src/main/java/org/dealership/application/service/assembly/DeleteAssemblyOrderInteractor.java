package org.dealership.application.service.assembly;

import org.dealership.application.port.in.assembly.DeleteAssemblyOrderUseCase;
import org.dealership.application.port.out.persistence.AssemblyOrderRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.AssemblyOrderId;

public class DeleteAssemblyOrderInteractor implements DeleteAssemblyOrderUseCase {
    private final AssemblyOrderRepository repository;

    public DeleteAssemblyOrderInteractor(AssemblyOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Response execute(Request request) {
        AssemblyOrderId id = new AssemblyOrderId(request.id());
        if (repository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Assembly order not found: " + id);
        }
        repository.deleteById(id);
        return new Response();
    }
}
