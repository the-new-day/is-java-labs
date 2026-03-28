package org.dealership.application.service.user;

import org.dealership.application.mapping.UserMapper;
import org.dealership.application.port.in.user.GetUserUseCase;
import org.dealership.application.port.out.persistence.UserRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.UserId;

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
