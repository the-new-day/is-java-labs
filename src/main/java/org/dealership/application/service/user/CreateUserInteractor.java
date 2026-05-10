package org.dealership.application.service.user;

import org.dealership.application.port.in.user.CreateUserUseCase;
import org.dealership.application.port.out.persistence.UserRepository;
import org.dealership.application.port.out.security.UserIdentityProvider;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.user.User;

public class CreateUserInteractor implements CreateUserUseCase {
    private final UserRepository userRepository;
    private final UserIdentityProvider userIdentityProvider;

    public CreateUserInteractor(UserRepository userRepository, UserIdentityProvider userIdentityProvider) {
        this.userRepository = userRepository;
        this.userIdentityProvider = userIdentityProvider;
    }

    @Override
    public Response execute(Request request) {
        UserId userId = userIdentityProvider.createUser(request.username(), request.password(), request.fullName());
        User user = new User(userId, request.fullName());
        userRepository.save(user);
        return new Response(userId.value());
    }
}
