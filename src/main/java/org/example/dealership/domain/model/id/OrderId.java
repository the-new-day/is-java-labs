package org.example.dealership.domain.model.id;

import org.example.dealership.domain.validation.DomainChecks;

import java.util.UUID;

public record OrderId(UUID value) {
    public OrderId {
        DomainChecks.notNull(value, "orderId");
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
