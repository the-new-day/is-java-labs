package org.example.dealership.application.port.in.user;

import java.util.UUID;

public interface DeleteUserUseCase {
    Response execute(Request request);

    record Request(UUID id) {}

    record Response() {}
}
