package org.dealership.application.service.user;

import org.dealership.application.mapping.UserMapper;
import org.dealership.application.port.in.user.UpdateUserUseCase;
import org.dealership.application.port.in.user.dto.UserDto;
import org.dealership.application.port.out.persistence.UserRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.UserId;

public class UpdateUserInteractor implements UpdateUserUseCase {
    private final UserRepository userRepository;

    public UpdateUserInteractor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Response execute(Request request) {
        UserDto dto = request.user();
        UserId userId = new UserId(dto.id());
        if (userRepository.findById(userId).isEmpty()) {
            throw new EntityNotFoundException("User not found: " + userId);
        }
        userRepository.save(UserMapper.mapFromDto(dto));
        return new Response();
    }
}
