package org.dealership.application.port.out.persistence;

import org.dealership.domain.model.assembly.AssemblyOrder;
import org.dealership.domain.model.id.AssemblyOrderId;

import java.util.List;
import java.util.Optional;

public interface AssemblyOrderRepository {
    AssemblyOrderId nextId();
    void save(AssemblyOrder assemblyOrder);
    Optional<AssemblyOrder> findById(AssemblyOrderId id);
    List<AssemblyOrder> findAll();
    void deleteById(AssemblyOrderId id);
}
