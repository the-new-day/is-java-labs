package org.dealership.application.service.user;

import org.dealership.application.port.in.user.GetUserUseCase;
import org.dealership.application.port.out.security.UserManager;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserInteractorTest {
    @Mock
    private UserManager userManager;

    @Test
    void shouldGetUser() {
        UUID id = UUID.randomUUID();
        when(userManager.findById(new UserId(id)))
                .thenReturn(Optional.of(new UserManager.UserRecord(id, "Full Name")));

        GetUserInteractor interactor = new GetUserInteractor(userManager);
        var response = interactor.execute(new GetUserUseCase.Request(id));

        assertEquals(id, response.user().id());
        assertEquals("Full Name", response.user().fullName());
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        UUID id = UUID.randomUUID();
        when(userManager.findById(new UserId(id))).thenReturn(Optional.empty());

        GetUserInteractor interactor = new GetUserInteractor(userManager);

        assertThrows(EntityNotFoundException.class,
                () -> interactor.execute(new GetUserUseCase.Request(id)));
    }
}
