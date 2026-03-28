package org.example.dealership.application.service.user;

import org.example.dealership.application.mapping.UserMapper;
import org.example.dealership.application.port.in.user.ListUsersUseCase;
import org.example.dealership.application.port.out.persistence.UserRepository;

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
