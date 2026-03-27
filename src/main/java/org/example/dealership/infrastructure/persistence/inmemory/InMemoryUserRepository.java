package org.example.dealership.infrastructure.persistence.inmemory;

import org.example.dealership.domain.model.user.User;
import org.example.dealership.domain.model.user.UserRole;
import org.example.dealership.domain.model.id.UserId;
import org.example.dealership.application.port.out.persistence.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryUserRepository extends InMemoryRepository<UserId, User> implements UserRepository {
    @Override
    public UserId nextId() {
        return new UserId(UUID.randomUUID());
    }

    @Override
    protected UserId getId(User entity) {
        return entity.getId();
    }

    @Override
    public void save(User user) {
        super.save(user);
    }

    @Override
    public Optional<User> findById(UserId id) {
        return super.findById(id);
    }

    @Override
    public List<User> findByRole(UserRole role) {
        return storage.values().stream()
                .filter(user -> user.getRole() == role)
                .toList();
    }

    @Override
    public List<User> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(UserId id) {
        super.deleteById(id);
    }
}
