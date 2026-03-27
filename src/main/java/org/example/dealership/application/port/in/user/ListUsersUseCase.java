package org.example.dealership.application.port.in.user;

import org.example.dealership.application.port.in.user.dto.UserDto;

import java.util.List;

public interface ListUsersUseCase {
    Response execute(Request request);

    record Request() {}

    record Response(List<UserDto> user) {}
}
