package org.example.dealership.domain.model.id;

import org.example.dealership.domain.validation.DomainChecks;

import java.util.UUID;

public record BrandId(UUID value) {
    public BrandId {
        DomainChecks.notNull(value, "brandId");
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
