package org.dealership.application.service.user;

import org.dealership.application.port.in.user.GetUserUseCase;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserInteractorTest {
    @Mock
    private UserRepository userRepository;

    @Test
    void shouldGetUser() {
        UUID userIdValue = UUID.randomUUID();
        User user = ServiceTestData.user(userIdValue);
        when(userRepository.findById(new UserId(userIdValue))).thenReturn(Optional.of(user));

        GetUserInteractor interactor = new GetUserInteractor(userRepository);
        var response = interactor.execute(new GetUserUseCase.Request(userIdValue));

        assertEquals(userIdValue, response.user().id());
    }
}
