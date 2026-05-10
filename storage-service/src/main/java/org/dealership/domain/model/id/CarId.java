package org.dealership.domain.model.id;

import org.dealership.domain.validation.DomainChecks;

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
