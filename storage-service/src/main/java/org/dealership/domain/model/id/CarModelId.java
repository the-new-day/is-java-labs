package org.dealership.domain.model.id;

import org.dealership.domain.validation.DomainChecks;

import java.util.UUID;

public record CarModelId(UUID value) {
    public CarModelId {
        DomainChecks.notNull(value, "carModelId");
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
