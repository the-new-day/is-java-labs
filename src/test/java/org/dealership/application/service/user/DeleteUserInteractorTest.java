package org.dealership.application.service.user;

import org.dealership.application.port.in.user.DeleteUserUseCase;
import org.dealership.application.port.out.persistence.UserRepository;
import org.dealership.application.service.user.DeleteUserInteractor;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteUserInteractorTest {
    @Mock
    private UserRepository userRepository;

    @Test
    void shouldDeleteUser() {
        UUID userIdValue = UUID.randomUUID();
        when(userRepository.findById(new UserId(userIdValue))).thenReturn(Optional.of(mock(User.class)));

        DeleteUserInteractor interactor = new DeleteUserInteractor(userRepository);
        var response = interactor.execute(new DeleteUserUseCase.Request(userIdValue));

        assertNotNull(response);
        verify(userRepository).deleteById(new UserId(userIdValue));
    }
}
