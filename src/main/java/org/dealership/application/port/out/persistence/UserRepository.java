package org.dealership.application.port.out.persistence;

import org.dealership.domain.model.user.User;
import org.dealership.domain.model.user.UserRole;
import org.dealership.domain.model.id.UserId;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    UserId nextId();
    void save(User user);
    Optional<User> findById(UserId id);
    List<User> findByRole(UserRole role);
    List<User> findAll();
    void deleteById(UserId id);
}
