package org.dealership.application.service.user;

import org.dealership.application.port.in.user.CreateUserUseCase;
import org.dealership.application.port.out.persistence.UserRepository;
import org.dealership.application.port.out.security.UserIdentityProvider;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserInteractorTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserIdentityProvider userIdentityProvider;

    @Test
    void shouldCreateUser() {
        UUID generatedId = UUID.randomUUID();
        when(userIdentityProvider.createUser(eq("johndoe"), eq("secret"), eq("John Doe")))
                .thenReturn(new UserId(generatedId));

        CreateUserInteractor interactor = new CreateUserInteractor(userRepository, userIdentityProvider);
        var response = interactor.execute(new CreateUserUseCase.Request("johndoe", "secret", "John Doe"));

        assertEquals(generatedId, response.id());
        verify(userIdentityProvider).createUser("johndoe", "secret", "John Doe");
        verify(userRepository).save(any(User.class));
    }
}
