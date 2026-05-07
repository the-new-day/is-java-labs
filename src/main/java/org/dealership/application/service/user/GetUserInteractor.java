package org.dealership.application.service.user;

import org.dealership.application.mapper.UserMapper;
import org.dealership.application.port.in.user.GetUserUseCase;
import org.dealership.application.port.out.persistence.UserRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.UserId;

public class GetUserInteractor implements GetUserUseCase {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public GetUserInteractor(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Response execute(Request request) {
        UserId userId = new UserId(request.id());
        return userRepository.findById(userId)
                .map(userMapper::toDto)
                .map(Response::new)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
    }
}
