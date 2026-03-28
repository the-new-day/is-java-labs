package org.dealership.application.port.in.user;

import org.dealership.application.port.in.user.dto.UserDto;

import java.util.UUID;

public interface GetUserUseCase {
    Response execute(Request request);

    record Request(UUID id) {}

    record Response(UserDto user) {}
}
