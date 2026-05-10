package org.dealership.application.service.user;

import org.dealership.application.port.in.user.ListUsersUseCase;
import org.dealership.application.port.out.security.UserManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListUsersInteractorTest {
    @Mock
    private UserManager userManager;

    @Test
    void shouldListUsers() {
        UUID id = UUID.randomUUID();
        when(userManager.listUsers())
                .thenReturn(List.of(new UserManager.UserRecord(id, "Full Name")));

        ListUsersInteractor interactor = new ListUsersInteractor(userManager);
        var response = interactor.execute(new ListUsersUseCase.Request());

        assertEquals(1, response.user().size());
        assertEquals(id, response.user().get(0).id());
    }
}
