package org.dealership.domain.model.id;

import org.dealership.domain.validation.DomainChecks;

import java.util.UUID;

public record TestDriveRequestId(UUID value) {
    public TestDriveRequestId {
        DomainChecks.notNull(value, "testDriveRequestId");
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
