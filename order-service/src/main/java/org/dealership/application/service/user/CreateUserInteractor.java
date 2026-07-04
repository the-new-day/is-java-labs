package org.dealership.application.service.user;

import org.dealership.application.port.in.user.CreateUserUseCase;
import org.dealership.application.port.out.security.UserManager;
import org.dealership.domain.model.id.UserId;

public class CreateUserInteractor implements CreateUserUseCase {

    private final UserManager userManager;

    public CreateUserInteractor(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public Response execute(Request request) {
        UserId userId = userManager.createUser(request.username(), request.password(), request.fullName());
        return new Response(userId.value());
    }
}
