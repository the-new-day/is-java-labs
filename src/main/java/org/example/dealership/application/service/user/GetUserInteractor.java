package org.example.dealership.application.service.user;

import org.example.dealership.application.mapping.UserMapper;
import org.example.dealership.application.port.in.user.GetUserUseCase;
import org.example.dealership.application.port.out.persistence.UserRepository;
import org.example.dealership.domain.exception.EntityNotFoundException;
import org.example.dealership.domain.model.id.UserId;

public class GetUserInteractor implements GetUserUseCase {
    private final UserRepository userRepository;

    public GetUserInteractor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Response execute(Request request) {
        UserId userId = new UserId(request.id());
        return userRepository.findById(userId)
                .map(UserMapper::mapToDto)
                .map(Response::new)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
    }
}
