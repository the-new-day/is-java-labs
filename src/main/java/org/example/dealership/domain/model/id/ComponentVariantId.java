package org.example.dealership.domain.model.id;

import org.example.dealership.domain.validation.DomainChecks;

import java.util.UUID;

public record ComponentVariantId(UUID value) {
    public ComponentVariantId {
        DomainChecks.notNull(value, "componentVariantId");
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
