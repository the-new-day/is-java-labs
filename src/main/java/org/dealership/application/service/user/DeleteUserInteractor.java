package org.dealership.application.service.user;

import org.dealership.application.port.in.user.DeleteUserUseCase;
import org.dealership.application.port.out.security.UserManager;
import org.dealership.domain.model.id.UserId;

public class DeleteUserInteractor implements DeleteUserUseCase {

    private final UserManager userManager;

    public DeleteUserInteractor(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public Response execute(Request request) {
        userManager.deleteUser(new UserId(request.id()));
        return new Response();
    }
}
