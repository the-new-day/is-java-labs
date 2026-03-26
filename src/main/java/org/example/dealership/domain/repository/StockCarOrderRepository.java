package org.example.dealership.domain.repository;

import org.example.dealership.domain.model.order.StockCarOrder;
import org.example.dealership.domain.model.id.OrderId;
import org.example.dealership.domain.model.id.UserId;

import java.util.List;
import java.util.Optional;

public interface StockCarOrderRepository {
    OrderId nextId();
    void save(StockCarOrder order);
    Optional<StockCarOrder> findById(OrderId id);
    List<StockCarOrder> findByClientId(UserId clientId);
    List<StockCarOrder> findByManagerId(UserId managerId);
    List<StockCarOrder> findAll();
    void deleteById(OrderId id);
}
