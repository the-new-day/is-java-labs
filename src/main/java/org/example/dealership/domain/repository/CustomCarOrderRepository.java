package org.example.dealership.domain.repository;

import org.example.dealership.domain.model.order.CustomCarOrder;
import org.example.dealership.domain.model.id.OrderId;
import org.example.dealership.domain.model.id.UserId;

import java.util.List;
import java.util.Optional;

public interface CustomCarOrderRepository {
    OrderId nextId();
    void save(CustomCarOrder order);
    Optional<CustomCarOrder> findById(OrderId id);
    List<CustomCarOrder> findByClientId(UserId clientId);
    List<CustomCarOrder> findByManagerId(UserId managerId);
    List<CustomCarOrder> findAll();
    void deleteById(OrderId id);
}
