package org.dealership.application.service.user;

import org.dealership.application.port.in.user.DeleteUserUseCase;
import org.dealership.application.port.out.persistence.UserRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.UserId;

public class DeleteUserInteractor implements DeleteUserUseCase {
    private final UserRepository userRepository;

    public DeleteUserInteractor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Response execute(Request request) {
        UserId userId = new UserId(request.id());
        if (userRepository.findById(userId).isEmpty()) {
            throw new EntityNotFoundException("User not found: " + userId);
        }
        userRepository.deleteById(userId);
        return new Response();
    }
}
