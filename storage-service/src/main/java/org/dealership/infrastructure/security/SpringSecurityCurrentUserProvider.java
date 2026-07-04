package org.dealership.infrastructure.security;

import org.dealership.application.port.out.security.CurrentUserProvider;
import org.dealership.domain.model.id.UserId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SpringSecurityCurrentUserProvider implements CurrentUserProvider {

    @Override
    public UserId currentUserId() {
        Jwt jwt = jwt();
        String sub = jwt.getSubject();
        if (sub == null) {
            throw new IllegalStateException("Authenticated principal has no subject claim");
        }
        return new UserId(UUID.fromString(sub));
    }

    @Override
    public String currentUserDisplayName() {
        Jwt jwt = jwt();
        String name = jwt.getClaimAsString("name");
        if (name != null && !name.isBlank()) {
            return name;
        }
        String preferredUsername = jwt.getClaimAsString("preferred_username");
        if (preferredUsername != null && !preferredUsername.isBlank()) {
            return preferredUsername;
        }
        return jwt.getSubject();
    }

    private static Jwt jwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("No authenticated principal in security context");
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof Jwt jwt)) {
            throw new IllegalStateException("Authenticated principal is not a JWT: " + principal);
        }
        return jwt;
    }
}
