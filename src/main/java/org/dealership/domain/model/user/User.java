package org.dealership.domain.model.user;

import org.dealership.domain.model.id.UserId;
import org.dealership.domain.validation.DomainChecks;

public class User {
    private final UserId id;
    private final String fullName;

    public User(UserId id, String fullName) {
        this.id = DomainChecks.notNull(id, "userId");
        this.fullName = DomainChecks.notBlank(fullName, "fullName");
    }

    public UserId getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }
}
