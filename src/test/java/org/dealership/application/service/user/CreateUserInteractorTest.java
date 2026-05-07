package org.dealership.application.service.user;

import org.dealership.application.mapper.UserRoleMapper;
import org.dealership.application.port.in.user.CreateUserUseCase;
import org.dealership.application.port.out.persistence.UserRepository;
import org.dealership.application.service.ServiceTestData;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.user.User;
import org.dealership.domain.model.user.UserRole;
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
    @Mock
    private UserRoleMapper userRoleMapper;

    @Test
    void shouldCreateUser() {
        UUID userIdValue = UUID.randomUUID();
        when(userRepository.nextId()).thenReturn(new UserId(userIdValue));
        when(userRoleMapper.toDomain(org.mockito.ArgumentMatchers.any())).thenReturn(UserRole.CLIENT);

        CreateUserInteractor interactor = new CreateUserInteractor(userRepository, userRoleMapper);
        var response = interactor.execute(new CreateUserUseCase.Request(
                "User",
                ServiceTestData.userDto(UUID.randomUUID(), "User", "CLIENT").role()
        ));

        assertNotNull(response.id());
        verify(userRepository).save(org.mockito.Mockito.any(User.class));
    }
}
