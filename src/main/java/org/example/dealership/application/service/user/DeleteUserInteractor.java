package org.example.dealership.application.service.user;

import org.example.dealership.application.port.in.user.DeleteUserUseCase;
import org.example.dealership.application.port.out.persistence.UserRepository;
import org.example.dealership.domain.exception.EntityNotFoundException;
import org.example.dealership.domain.model.id.UserId;

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
