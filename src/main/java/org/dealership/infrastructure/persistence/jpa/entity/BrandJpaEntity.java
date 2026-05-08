package org.dealership.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.BatchSize;

import java.util.UUID;

@Entity
@Table(name = "brands")
@BatchSize(size = 32)
public class BrandJpaEntity extends BaseJpaEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    protected BrandJpaEntity() {
    }

    public BrandJpaEntity(UUID id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
