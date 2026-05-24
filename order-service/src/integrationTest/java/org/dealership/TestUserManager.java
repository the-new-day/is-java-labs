package org.dealership;

import org.dealership.application.port.out.security.UserManager;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.UserId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TestUserManager implements UserManager {

    private final Map<UUID, String> seed;
    private final Map<UUID, String> dynamic = new ConcurrentHashMap<>();
    private final Set<UUID> deleted = Collections.synchronizedSet(new HashSet<>());

    public TestUserManager(Map<UUID, String> seed) {
        this.seed = Map.copyOf(seed);
    }

    public void reset() {
        dynamic.clear();
        deleted.clear();
    }

    @Override
    public UserId createUser(String username, String password, String fullName) {
        UUID id = UUID.randomUUID();
        dynamic.put(id, fullName);
        return new UserId(id);
    }

    @Override
    public Optional<UserRecord> findById(UserId userId) {
        return lookupFullName(userId.value())
                .map(name -> new UserRecord(userId.value(), name));
    }

    @Override
    public List<UserRecord> listUsers() {
        List<UserRecord> result = new ArrayList<>();
        seed.forEach((id, name) -> {
            if (!deleted.contains(id)) result.add(new UserRecord(id, name));
        });
        dynamic.forEach((id, name) -> {
            if (!deleted.contains(id)) result.add(new UserRecord(id, name));
        });
        return result;
    }

    @Override
    public void updateUser(UserId userId, String fullName) {
        if (lookupFullName(userId.value()).isEmpty()) {
            throw new EntityNotFoundException("User not found: " + userId);
        }
        dynamic.put(userId.value(), fullName);
    }

    @Override
    public void deleteUser(UserId userId) {
        if (lookupFullName(userId.value()).isEmpty()) {
            throw new EntityNotFoundException("User not found: " + userId);
        }
        deleted.add(userId.value());
    }

    private Optional<String> lookupFullName(UUID id) {
        if (deleted.contains(id)) return Optional.empty();
        if (dynamic.containsKey(id)) return Optional.of(dynamic.get(id));
        if (seed.containsKey(id)) return Optional.of(seed.get(id));
        return Optional.empty();
    }
}
