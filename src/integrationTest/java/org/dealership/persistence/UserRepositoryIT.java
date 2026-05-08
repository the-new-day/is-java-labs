package org.dealership.persistence;

import org.dealership.AbstractIntegrationTest;
import org.dealership.domain.model.user.UserRole;
import org.dealership.infrastructure.persistence.jpa.entity.UserJpaEntity;
import org.dealership.infrastructure.persistence.jpa.repository.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class UserRepositoryIT extends AbstractIntegrationTest {

    private static final UUID CLIENT_ID = UUID.fromString("00000000-0000-0000-0000-000000000301");
    private static final UUID MANAGER_ID = UUID.fromString("00000000-0000-0000-0000-000000000302");

    @Autowired
    private UserJpaRepository userRepository;

    @Test
    void findByIdAndRemovedFalse_existingClient_returnsUser() {
        Optional<UserJpaEntity> result = userRepository.findByIdAndRemovedFalse(CLIENT_ID);

        assertThat(result).isPresent();
        assertThat(result.get().getFullName()).isEqualTo("Ivan Petrov");
        assertThat(result.get().getRole()).isEqualTo(UserRole.CLIENT);
    }

    @Test
    void findByIdAndRemovedFalse_nonExistingId_returnsEmpty() {
        Optional<UserJpaEntity> result = userRepository.findByIdAndRemovedFalse(UUID.randomUUID());

        assertThat(result).isEmpty();
    }

    @Test
    void findAllByRemovedFalse_returnsAllSeedUsers() {
        List<UserJpaEntity> users = userRepository.findAllByRemovedFalse();

        assertThat(users).hasSize(4);
    }

    @Test
    void findAllByRoleAndRemovedFalse_clientRole_returnsOnlyClients() {
        List<UserJpaEntity> clients = userRepository.findAllByRoleAndRemovedFalse(UserRole.CLIENT);

        assertThat(clients).hasSize(1);
        assertThat(clients.get(0).getId()).isEqualTo(CLIENT_ID);
    }

    @Test
    void findAllByRoleAndRemovedFalse_managerRole_returnsOnlyManagers() {
        List<UserJpaEntity> managers = userRepository.findAllByRoleAndRemovedFalse(UserRole.MANAGER);

        assertThat(managers).hasSize(1);
        assertThat(managers.get(0).getId()).isEqualTo(MANAGER_ID);
        assertThat(managers.get(0).getFullName()).isEqualTo("Maria Manager");
    }

    @Test
    void save_persistsNewUser() {
        UUID newId = UUID.randomUUID();
        UserJpaEntity newUser = new UserJpaEntity(newId, "New User", UserRole.CLIENT);

        userRepository.saveAndFlush(newUser);

        Optional<UserJpaEntity> found = userRepository.findByIdAndRemovedFalse(newId);
        assertThat(found).isPresent();
        assertThat(found.get().getFullName()).isEqualTo("New User");
        assertThat(found.get().getRole()).isEqualTo(UserRole.CLIENT);
    }

    @Test
    void markRemoved_userNoLongerReturnedByActiveFinder() {
        UserJpaEntity user = userRepository.findByIdAndRemovedFalse(CLIENT_ID).orElseThrow();
        user.markRemoved();
        userRepository.saveAndFlush(user);

        Optional<UserJpaEntity> result = userRepository.findByIdAndRemovedFalse(CLIENT_ID);

        assertThat(result).isEmpty();
    }

    @Test
    void markRemoved_removedUserExcludedFromList() {
        UserJpaEntity user = userRepository.findByIdAndRemovedFalse(CLIENT_ID).orElseThrow();
        user.markRemoved();
        userRepository.saveAndFlush(user);

        List<UserJpaEntity> activeUsers = userRepository.findAllByRemovedFalse();

        assertThat(activeUsers).extracting(UserJpaEntity::getId).doesNotContain(CLIENT_ID);
    }
}
