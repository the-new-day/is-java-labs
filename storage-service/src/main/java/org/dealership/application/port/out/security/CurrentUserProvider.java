package org.dealership.application.port.out.security;

import org.dealership.domain.model.id.UserId;

public interface CurrentUserProvider {
    UserId currentUserId();

    String currentUserDisplayName();
}
