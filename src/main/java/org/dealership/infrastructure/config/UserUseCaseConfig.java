package org.dealership.infrastructure.config;

import org.dealership.application.port.in.user.CreateUserUseCase;
import org.dealership.application.port.in.user.DeleteUserUseCase;
import org.dealership.application.port.in.user.GetUserUseCase;
import org.dealership.application.port.in.user.ListUsersUseCase;
import org.dealership.application.port.in.user.UpdateUserUseCase;
import org.dealership.application.port.out.security.UserManager;
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
    public CreateUserUseCase createUserUseCase(UserManager userManager) {
        return new CreateUserInteractor(userManager);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(UserManager userManager) {
        return new DeleteUserInteractor(userManager);
    }

    @Bean
    public GetUserUseCase getUserUseCase(UserManager userManager) {
        return new GetUserInteractor(userManager);
    }

    @Bean
    public ListUsersUseCase listUsersUseCase(UserManager userManager) {
        return new ListUsersInteractor(userManager);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(UserManager userManager) {
        return new UpdateUserInteractor(userManager);
    }
}
