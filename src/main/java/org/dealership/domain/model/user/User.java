package org.dealership.domain.model.user;

import org.dealership.domain.model.id.UserId;
import org.dealership.domain.validation.DomainChecks;

public class User {
    private final UserId id;
    private final String fullName;
    private final UserRole role;

    public User(UserId id, String fullName, UserRole role) {
        this.id = DomainChecks.notNull(id, "userId");
        this.fullName = DomainChecks.notBlank(fullName, "fullName");
        this.role = DomainChecks.notNull(role, "role");
    }

    public UserId getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public UserRole getRole() {
        return role;
    }
}
