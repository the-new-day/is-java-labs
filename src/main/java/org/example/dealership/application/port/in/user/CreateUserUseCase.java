package org.example.dealership.application.port.in.user;

import org.example.dealership.application.port.in.user.dto.UserRoleDto;

import java.util.UUID;

public interface CreateUserUseCase {
    Response execute(Request request);

    record Request(String fullName, UserRoleDto role) {}

    record Response(UUID id) {}
}
