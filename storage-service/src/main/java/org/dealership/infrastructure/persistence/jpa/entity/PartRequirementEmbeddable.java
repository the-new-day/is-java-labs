package org.dealership.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.UUID;

@Embeddable
public class PartRequirementEmbeddable {

    @Column(name = "part_id", nullable = false)
    private UUID partId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    protected PartRequirementEmbeddable() {
    }

    public PartRequirementEmbeddable(UUID partId, int quantity) {
        this.partId = partId;
        this.quantity = quantity;
    }

    public UUID getPartId() {
        return partId;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PartRequirementEmbeddable that)) return false;
        return Objects.equals(partId, that.partId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partId);
    }
}
