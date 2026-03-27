package org.example.dealership.infrastructure.persistence.inmemory;

import org.example.dealership.domain.model.order.StockCarOrder;
import org.example.dealership.domain.model.id.OrderId;
import org.example.dealership.domain.model.id.UserId;
import org.example.dealership.application.port.out.persistence.StockCarOrderRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryStockCarOrderRepository extends InMemoryRepository<OrderId, StockCarOrder> implements StockCarOrderRepository {
    @Override
    public OrderId nextId() {
        return new OrderId(UUID.randomUUID());
    }

    @Override
    protected OrderId getId(StockCarOrder entity) {
        return entity.getId();
    }

    @Override
    public void save(StockCarOrder order) {
        super.save(order);
    }

    @Override
    public Optional<StockCarOrder> findById(OrderId id) {
        return super.findById(id);
    }

    @Override
    public List<StockCarOrder> findByClientId(UserId clientId) {
        return storage.values().stream()
                .filter(order -> order.getClientId().equals(clientId))
                .toList();
    }

    @Override
    public List<StockCarOrder> findByManagerId(UserId managerId) {
        return storage.values().stream()
                .filter(order -> order.getManagerId().equals(managerId))
                .toList();
    }

    @Override
    public List<StockCarOrder> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(OrderId id) {
        super.deleteById(id);
    }
}
