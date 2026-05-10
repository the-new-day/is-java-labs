package org.dealership.domain.model.assembly;

import org.dealership.domain.validation.DomainChecks;

import java.util.UUID;

public final class PartRequirement {
    private final UUID partId;
    private final int quantity;

    public PartRequirement(UUID partId, int quantity) {
        this.partId = DomainChecks.notNull(partId, "partId");
        this.quantity = DomainChecks.positive(quantity, "quantity");
    }

    public UUID getPartId() {
        return partId;
    }

    public int getQuantity() {
        return quantity;
    }
}
