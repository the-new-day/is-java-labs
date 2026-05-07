package org.dealership.application.service.user;

import org.dealership.application.mapper.UserMapper;
import org.dealership.application.port.in.user.ListUsersUseCase;
import org.dealership.application.port.out.persistence.UserRepository;

public class ListUsersInteractor implements ListUsersUseCase {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public ListUsersInteractor(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Response execute(Request request) {
        return new Response(
                userRepository.findAll().stream()
                        .map(userMapper::toDto)
                        .toList()
        );
    }
}
