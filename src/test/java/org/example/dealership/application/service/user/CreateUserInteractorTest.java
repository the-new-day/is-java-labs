package org.example.dealership.application.service.user;

import org.example.dealership.application.port.in.user.CreateUserUseCase;
import org.example.dealership.application.port.out.persistence.UserRepository;
import org.example.dealership.application.service.ServiceTestData;
import org.example.dealership.domain.model.id.UserId;
import org.example.dealership.domain.model.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserInteractorTest {
    @Mock
    private UserRepository userRepository;

    @Test
    void shouldCreateUser() {
        UUID userIdValue = UUID.randomUUID();
        when(userRepository.nextId()).thenReturn(new UserId(userIdValue));

        CreateUserInteractor interactor = new CreateUserInteractor(userRepository);
        var response = interactor.execute(new CreateUserUseCase.Request(
                "User",
                ServiceTestData.userDto(UUID.randomUUID(), "User", "CLIENT").role()
        ));

        assertNotNull(response.id());
        verify(userRepository).save(org.mockito.Mockito.any(User.class));
    }
}
