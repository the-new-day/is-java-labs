package org.dealership.application.service.assembly;

import org.dealership.application.mapper.AssemblyOrderMapper;
import org.dealership.application.port.in.assembly.CreateAssemblyOrderUseCase;
import org.dealership.application.port.in.assembly.dto.NewAssemblyOrderDto;
import org.dealership.application.port.out.persistence.AssemblyOrderRepository;
import org.dealership.domain.model.assembly.AssemblyOrder;
import org.dealership.domain.model.id.AssemblyOrderId;

public class CreateAssemblyOrderInteractor implements CreateAssemblyOrderUseCase {
    private final AssemblyOrderRepository repository;
    private final AssemblyOrderMapper mapper;

    public CreateAssemblyOrderInteractor(AssemblyOrderRepository repository, AssemblyOrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Response execute(Request request) {
        NewAssemblyOrderDto dto = request.newAssemblyOrder();
        AssemblyOrderId id = repository.nextId();
        AssemblyOrder order = mapper.toDomain(id, dto);
        repository.save(order);
        return new Response(id.value());
    }
}
