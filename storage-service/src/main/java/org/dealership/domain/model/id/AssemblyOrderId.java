package org.dealership.domain.model.id;

import org.dealership.domain.validation.DomainChecks;

import java.util.UUID;

public record AssemblyOrderId(UUID value) {
    public AssemblyOrderId {
        DomainChecks.notNull(value, "assemblyOrderId");
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
