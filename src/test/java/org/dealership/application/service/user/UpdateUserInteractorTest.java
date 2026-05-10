package org.dealership.application.service.user;

import org.dealership.application.port.in.user.UpdateUserUseCase;
import org.dealership.application.port.out.security.UserManager;
import org.dealership.application.service.ServiceTestData;
import org.dealership.domain.model.id.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpdateUserInteractorTest {
    @Mock
    private UserManager userManager;

    @Test
    void shouldUpdateUser() {
        UUID id = UUID.randomUUID();

        UpdateUserInteractor interactor = new UpdateUserInteractor(userManager);
        var response = interactor.execute(new UpdateUserUseCase.Request(
                ServiceTestData.userDto(id, "Updated Name")
        ));

        assertNotNull(response);
        verify(userManager).updateUser(new UserId(id), "Updated Name");
    }
}
