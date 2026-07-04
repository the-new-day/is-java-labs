package org.dealership.application.service.user;

import org.dealership.application.port.in.user.DeleteUserUseCase;
import org.dealership.application.port.out.security.UserManager;
import org.dealership.domain.model.id.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteUserInteractorTest {
    @Mock
    private UserManager userManager;

    @Test
    void shouldDeleteUser() {
        UUID id = UUID.randomUUID();

        DeleteUserInteractor interactor = new DeleteUserInteractor(userManager);
        var response = interactor.execute(new DeleteUserUseCase.Request(id));

        assertNotNull(response);
        verify(userManager).deleteUser(new UserId(id));
    }
}
