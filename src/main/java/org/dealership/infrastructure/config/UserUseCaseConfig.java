package org.dealership.infrastructure.config;

import org.dealership.application.mapper.UserMapper;
import org.dealership.application.port.in.user.CreateUserUseCase;
import org.dealership.application.port.in.user.DeleteUserUseCase;
import org.dealership.application.port.in.user.GetUserUseCase;
import org.dealership.application.port.in.user.ListUsersUseCase;
import org.dealership.application.port.in.user.UpdateUserUseCase;
import org.dealership.application.port.out.persistence.UserRepository;
import org.dealership.application.service.user.CreateUserInteractor;
import org.dealership.application.service.user.DeleteUserInteractor;
import org.dealership.application.service.user.GetUserInteractor;
import org.dealership.application.service.user.ListUsersInteractor;
import org.dealership.application.service.user.UpdateUserInteractor;
import org.dealership.domain.model.user.RandomUserSelectionStrategy;
import org.dealership.domain.model.user.UserSelectionStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCaseConfig {

    @Bean
    public UserSelectionStrategy userSelectionStrategy() {
        return new RandomUserSelectionStrategy();
    }

    @Bean
    public CreateUserUseCase createUserUseCase(UserRepository userRepository) {
        return new CreateUserInteractor(userRepository);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(UserRepository userRepository) {
        return new DeleteUserInteractor(userRepository);
    }

    @Bean
    public GetUserUseCase getUserUseCase(UserRepository userRepository, UserMapper userMapper) {
        return new GetUserInteractor(userRepository, userMapper);
    }

    @Bean
    public ListUsersUseCase listUsersUseCase(UserRepository userRepository, UserMapper userMapper) {
        return new ListUsersInteractor(userRepository, userMapper);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(UserRepository userRepository, UserMapper userMapper) {
        return new UpdateUserInteractor(userRepository, userMapper);
    }
}
