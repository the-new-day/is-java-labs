package org.dealership.application.service.user;

import org.dealership.application.port.in.user.ListUsersUseCase;
import org.dealership.application.port.out.persistence.UserRepository;
import org.dealership.application.service.ServiceTestData;
import org.dealership.domain.model.user.User;
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
    private UserRepository userRepository;

    @Test
    void shouldListUsers() {
        User user = ServiceTestData.user(UUID.randomUUID());
        when(userRepository.findAll()).thenReturn(List.of(user));

        ListUsersInteractor interactor = new ListUsersInteractor(userRepository);
        var response = interactor.execute(new ListUsersUseCase.Request());

        assertEquals(1, response.user().size());
    }
}
