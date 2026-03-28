package org.example.dealership.domain.model.order;

import org.example.dealership.domain.exception.EntityNotFoundException;
import org.example.dealership.domain.model.id.UserId;

import java.util.List;
import java.util.Random;

public class RandomUserSelectionStrategy implements UserSelectionStrategy {
    private final Random random = new Random();

    @Override
    public UserId selectUser(List<UserId> availableManagers) {
        if (availableManagers == null || availableManagers.isEmpty()) {
            throw new EntityNotFoundException("Cannot assign manager: no available managers found");
        }

        int index = random.nextInt(availableManagers.size());
        return availableManagers.get(index);
    }
}
