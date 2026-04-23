package org.dealership.infrastructure.persistence.jpa.adapter;

import org.dealership.application.port.out.persistence.UserRepository;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.user.User;
import org.dealership.domain.model.user.UserRole;
import org.dealership.infrastructure.persistence.jpa.mapper.UserJpaMapper;
import org.dealership.infrastructure.persistence.jpa.repository.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepositoryJpaAdapter implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final UserJpaMapper userJpaMapper;

    public UserRepositoryJpaAdapter(
            UserJpaRepository userJpaRepository,
            UserJpaMapper userJpaMapper
    ) {
        this.userJpaRepository = userJpaRepository;
        this.userJpaMapper = userJpaMapper;
    }

    @Override
    public UserId nextId() {
        return new UserId(UUID.randomUUID());
    }

    @Override
    public void save(User user) {
        userJpaRepository.save(userJpaMapper.toEntity(user));
    }

    @Override
    public Optional<User> findById(UserId id) {
        return userJpaRepository.findByIdAndRemovedFalse(id.value())
                .map(userJpaMapper::toDomain);
    }

    @Override
    public List<User> findByRole(UserRole role) {
        return userJpaRepository.findAllByRoleAndRemovedFalse(role).stream()
                .map(userJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAllByRemovedFalse().stream()
                .map(userJpaMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UserId id) {
        userJpaRepository.findByIdAndRemovedFalse(id.value())
                .ifPresent(entity -> {
                    entity.markRemoved();
                    userJpaRepository.save(entity);
                });
    }
}
