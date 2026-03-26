package org.example.dealership.domain.model.id;

import org.example.dealership.domain.validation.DomainChecks;

import java.util.UUID;

public record UserId(UUID value) {
    public UserId {
        DomainChecks.notNull(value, "userId");
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
