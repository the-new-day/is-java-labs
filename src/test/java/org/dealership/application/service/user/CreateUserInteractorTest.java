package org.dealership.application.service.user;

import org.dealership.application.port.in.user.CreateUserUseCase;
import org.dealership.application.port.out.persistence.UserRepository;
import org.dealership.domain.model.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateUserInteractorTest {
    @Mock
    private UserRepository userRepository;

    @Test
    void shouldCreateUser() {
        UUID userIdValue = UUID.randomUUID();

        CreateUserInteractor interactor = new CreateUserInteractor(userRepository);
        var response = interactor.execute(new CreateUserUseCase.Request(userIdValue, "User"));

        assertEquals(userIdValue, response.id());
        verify(userRepository).save(org.mockito.Mockito.any(User.class));
    }
}
