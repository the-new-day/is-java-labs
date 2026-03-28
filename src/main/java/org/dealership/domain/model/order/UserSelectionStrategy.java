package org.dealership.domain.model.order;

import org.dealership.domain.model.id.UserId;

import java.util.List;

public interface UserSelectionStrategy {
    UserId selectUser(List<UserId> availableManagerIds);
}
