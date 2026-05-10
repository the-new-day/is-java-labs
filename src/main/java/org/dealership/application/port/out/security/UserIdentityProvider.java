package org.dealership.application.port.out.security;

import org.dealership.domain.model.id.UserId;

public interface UserIdentityProvider {
    UserId createUser(String username, String password, String fullName);
}
