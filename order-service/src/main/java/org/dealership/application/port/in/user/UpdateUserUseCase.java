package org.dealership.application.port.in.user;

import org.dealership.application.port.in.user.dto.UserDto;

public interface UpdateUserUseCase {
    Response execute(Request request);

    record Request(UserDto user) {}

    record Response() {}
}
