package org.dealership.application.service.user;

import org.dealership.application.port.in.user.UpdateUserUseCase;
import org.dealership.application.port.in.user.dto.UserDto;
import org.dealership.application.port.out.security.UserManager;
import org.dealership.domain.model.id.UserId;

public class UpdateUserInteractor implements UpdateUserUseCase {

    private final UserManager userManager;

    public UpdateUserInteractor(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public Response execute(Request request) {
        UserDto dto = request.user();
        userManager.updateUser(new UserId(dto.id()), dto.fullName());
        return new Response();
    }
}
