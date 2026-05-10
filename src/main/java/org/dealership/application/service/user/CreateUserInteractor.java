package org.dealership.application.service.user;

import org.dealership.application.port.in.user.CreateUserUseCase;
import org.dealership.application.port.out.persistence.UserRepository;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.user.User;

public class CreateUserInteractor implements CreateUserUseCase {
    private final UserRepository userRepository;

    public CreateUserInteractor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Response execute(Request request) {
        UserId userId = new UserId(request.id());
        User user = new User(userId, request.fullName());
        userRepository.save(user);
        return new Response(userId.value());
    }
}
