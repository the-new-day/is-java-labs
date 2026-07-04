package org.dealership.application.service.user;

import org.dealership.application.port.in.user.ListUsersUseCase;
import org.dealership.application.port.in.user.dto.UserDto;
import org.dealership.application.port.out.security.UserManager;

import java.util.List;

public class ListUsersInteractor implements ListUsersUseCase {

    private final UserManager userManager;

    public ListUsersInteractor(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public Response execute(Request request) {
        List<UserDto> users = userManager.listUsers().stream()
                .map(r -> new UserDto(r.id(), r.fullName()))
                .toList();
        return new Response(users);
    }
}
