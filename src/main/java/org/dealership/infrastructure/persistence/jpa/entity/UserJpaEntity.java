package org.dealership.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "users")
public class UserJpaEntity extends BaseJpaEntity {
    @Column(name = "full_name", nullable = false)
    private String fullName;

    protected UserJpaEntity() {
    }

    public UserJpaEntity(UUID id, String fullName) {
        super(id);
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
