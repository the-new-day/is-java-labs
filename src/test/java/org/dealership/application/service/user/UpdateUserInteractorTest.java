package org.dealership.application.service.user;

import org.dealership.application.port.in.user.UpdateUserUseCase;
import org.dealership.application.port.out.persistence.UserRepository;
import org.dealership.application.service.ServiceTestData;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserInteractorTest {
    @Mock
    private UserRepository userRepository;

    @Test
    void shouldUpdateUser() {
        UUID userIdValue = UUID.randomUUID();
        User existing = ServiceTestData.user(userIdValue);
        when(userRepository.findById(new UserId(userIdValue))).thenReturn(Optional.of(existing));

        UpdateUserInteractor interactor = new UpdateUserInteractor(userRepository);
        var response = interactor.execute(new UpdateUserUseCase.Request(
                ServiceTestData.userDto(userIdValue, "User", "CLIENT")
        ));

        assertNotNull(response);
        verify(userRepository).save(org.mockito.Mockito.any(User.class));
    }
}
