package org.dealership.infrastructure.persistence.inmemory;

import org.dealership.domain.model.order.CustomCarOrder;
import org.dealership.domain.model.id.OrderId;
import org.dealership.domain.model.id.UserId;
import org.dealership.application.port.out.persistence.CustomCarOrderRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryCustomCarOrderRepository extends InMemoryRepository<OrderId, CustomCarOrder> implements CustomCarOrderRepository {
    @Override
    public OrderId nextId() {
        return new OrderId(UUID.randomUUID());
    }

    @Override
    protected OrderId getId(CustomCarOrder entity) {
        return entity.getId();
    }

    @Override
    public void save(CustomCarOrder order) {
        super.save(order);
    }

    @Override
    public Optional<CustomCarOrder> findById(OrderId id) {
        return super.findById(id);
    }

    @Override
    public List<CustomCarOrder> findByClientId(UserId clientId) {
        return storage.values().stream()
                .filter(order -> order.getClientId().equals(clientId))
                .toList();
    }

    @Override
    public List<CustomCarOrder> findByManagerId(UserId managerId) {
        return storage.values().stream()
                .filter(order -> order.getManagerId().equals(managerId))
                .toList();
    }

    @Override
    public List<CustomCarOrder> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(OrderId id) {
        super.deleteById(id);
    }
}
