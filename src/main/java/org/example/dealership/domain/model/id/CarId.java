package org.example.dealership.domain.model.id;

import org.example.dealership.domain.validation.DomainChecks;

import java.util.UUID;

public record CarId(UUID value) {
    public CarId {
        DomainChecks.notNull(value, "carId");
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
