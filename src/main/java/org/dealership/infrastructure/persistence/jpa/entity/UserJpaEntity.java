package org.dealership.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import org.dealership.domain.model.user.UserRole;

import java.util.UUID;

@Entity
@Table(name = "users")
public class UserJpaEntity extends BaseJpaEntity {
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    protected UserJpaEntity() {
    }

    public UserJpaEntity(UUID id, String fullName, UserRole role) {
        super(id);
        this.fullName = fullName;
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
