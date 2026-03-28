package org.example.dealership.application.service.user;

import org.example.dealership.application.mapping.UserRoleMapper;
import org.example.dealership.application.port.in.user.CreateUserUseCase;
import org.example.dealership.application.port.out.persistence.UserRepository;
import org.example.dealership.domain.model.id.UserId;
import org.example.dealership.domain.model.user.User;

public class CreateUserInteractor implements CreateUserUseCase {
    private final UserRepository userRepository;

    public CreateUserInteractor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Response execute(Request request) {
        UserId userId = userRepository.nextId();
        User user = new User(
                userId,
                request.fullName(),
                UserRoleMapper.mapFromDto(request.role())
        );
        userRepository.save(user);
        return new Response(userId.value());
    }
}
