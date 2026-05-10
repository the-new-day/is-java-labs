package org.dealership.application.service.user;

import org.dealership.application.port.in.user.CreateUserUseCase;
import org.dealership.application.port.out.security.UserManager;
import org.dealership.domain.model.id.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserInteractorTest {
    @Mock
    private UserManager userManager;

    @Test
    void shouldCreateUser() {
        UUID generatedId = UUID.randomUUID();
        when(userManager.createUser("johndoe", "secret", "John Doe"))
                .thenReturn(new UserId(generatedId));

        CreateUserInteractor interactor = new CreateUserInteractor(userManager);
        var response = interactor.execute(new CreateUserUseCase.Request("johndoe", "secret", "John Doe"));

        assertEquals(generatedId, response.id());
        verify(userManager).createUser("johndoe", "secret", "John Doe");
    }
}
