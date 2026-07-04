package org.dealership.domain.model.id;

import org.dealership.domain.validation.DomainChecks;

import java.util.UUID;

public record SparePartId(UUID value) {
    public SparePartId {
        DomainChecks.notNull(value, "sparePartId");
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
