package org.dealership.persistence;

import org.dealership.AbstractIntegrationTest;
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

    @Autowired
    private UserJpaRepository userRepository;

    @Test
    void findByIdAndRemovedFalse_existingClient_returnsUser() {
        Optional<UserJpaEntity> result = userRepository.findByIdAndRemovedFalse(CLIENT_ID);

        assertThat(result).isPresent();
        assertThat(result.get().getFullName()).isEqualTo("Ivan Petrov");
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
    void save_persistsNewUser() {
        UUID newId = UUID.randomUUID();
        UserJpaEntity newUser = new UserJpaEntity(newId, "New User");

        userRepository.saveAndFlush(newUser);

        Optional<UserJpaEntity> found = userRepository.findByIdAndRemovedFalse(newId);
        assertThat(found).isPresent();
        assertThat(found.get().getFullName()).isEqualTo("New User");
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
