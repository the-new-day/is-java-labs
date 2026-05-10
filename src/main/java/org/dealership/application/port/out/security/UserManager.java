package org.dealership.application.port.out.security;

import org.dealership.domain.model.id.UserId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserManager {

    record UserRecord(UUID id, String fullName) {}

    UserId createUser(String username, String password, String fullName);

    Optional<UserRecord> findById(UserId id);

    List<UserRecord> listUsers();

    void updateUser(UserId id, String fullName);

    void deleteUser(UserId id);
}
