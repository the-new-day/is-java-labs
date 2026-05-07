package org.dealership.application.service.user;

import org.dealership.application.mapper.UserRoleMapper;
import org.dealership.application.port.in.user.CreateUserUseCase;
import org.dealership.application.port.out.persistence.UserRepository;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.user.User;

public class CreateUserInteractor implements CreateUserUseCase {
    private final UserRepository userRepository;
    private final UserRoleMapper userRoleMapper;

    public CreateUserInteractor(UserRepository userRepository, UserRoleMapper userRoleMapper) {
        this.userRepository = userRepository;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public Response execute(Request request) {
        UserId userId = userRepository.nextId();
        User user = new User(
                userId,
                request.fullName(),
                userRoleMapper.toDomain(request.role())
        );
        userRepository.save(user);
        return new Response(userId.value());
    }
}
