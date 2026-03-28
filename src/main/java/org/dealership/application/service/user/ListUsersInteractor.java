package org.dealership.application.service.user;

import org.dealership.application.mapping.UserMapper;
import org.dealership.application.port.in.user.ListUsersUseCase;
import org.dealership.application.port.out.persistence.UserRepository;

public class ListUsersInteractor implements ListUsersUseCase {
    private final UserRepository userRepository;

    public ListUsersInteractor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Response execute(Request request) {
        return new Response(
                userRepository.findAll().stream()
                        .map(UserMapper::mapToDto)
                        .toList()
        );
    }
}
