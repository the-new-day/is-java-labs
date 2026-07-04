package org.dealership.application.service.user;

import org.dealership.application.port.in.user.GetUserUseCase;
import org.dealership.application.port.in.user.dto.UserDto;
import org.dealership.application.port.out.security.UserManager;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.UserId;

public class GetUserInteractor implements GetUserUseCase {

    private final UserManager userManager;

    public GetUserInteractor(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public Response execute(Request request) {
        UserId userId = new UserId(request.id());
        return userManager.findById(userId)
                .map(r -> new UserDto(r.id(), r.fullName()))
                .map(Response::new)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
    }
}
