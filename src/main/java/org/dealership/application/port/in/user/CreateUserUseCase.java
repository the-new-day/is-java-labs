package org.dealership.application.port.in.user;

import java.util.UUID;

public interface CreateUserUseCase {
    Response execute(Request request);

    record Request(UUID id, String fullName) {}

    record Response(UUID id) {}
}
