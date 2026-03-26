package org.example.dealership.domain.model.order;

import org.example.dealership.domain.model.id.OrderId;
import org.example.dealership.domain.model.id.UserId;
import org.example.dealership.domain.validation.DomainChecks;

public abstract class Order {
    private final OrderId id;
    private final UserId clientId;
    private final UserId managerId;

    public Order(OrderId id, UserId clientId, UserId managerId) {
        this.id = DomainChecks.notNull(id, "orderId");
        this.clientId = DomainChecks.notNull(clientId, "clientId");
        this.managerId = DomainChecks.notNull(managerId, "managerId");
    }

    public OrderId getId() {
        return id;
    }

    public UserId getClientId() {
        return clientId;
    }

    public UserId getManagerId() {
        return managerId;
    }
}
