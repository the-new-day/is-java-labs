package org.dealership.infrastructure.persistence.jpa.adapter;

import org.dealership.application.port.out.persistence.UserRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.user.User;
import org.dealership.infrastructure.persistence.jpa.entity.UserJpaEntity;
import org.dealership.infrastructure.persistence.jpa.mapper.UserJpaMapper;
import org.dealership.infrastructure.persistence.jpa.repository.UserJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public class UserJpaAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserJpaMapper userJpaMapper;

    public UserJpaAdapter(UserJpaRepository userJpaRepository, UserJpaMapper userJpaMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userJpaMapper = userJpaMapper;
    }

    @Override
    public UserId nextId() {
        return new UserId(UUID.randomUUID());
    }

    @Override
    @Transactional
    public void save(User user) {
        Optional<UserJpaEntity> existing = userJpaRepository.findByIdAndRemovedFalse(user.getId().value());
        if (existing.isPresent()) {
            UserJpaEntity entity = existing.get();
            entity.setFullName(user.getFullName());
            userJpaRepository.save(entity);
        } else {
            userJpaRepository.save(userJpaMapper.toEntity(user));
        }
    }

    @Override
    public Optional<User> findById(UserId id) {
        return userJpaRepository.findByIdAndRemovedFalse(id.value())
                .map(userJpaMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAllByRemovedFalse().stream()
                .map(userJpaMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(UserId id) {
        UserJpaEntity entity = userJpaRepository.findByIdAndRemovedFalse(id.value())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
        entity.markRemoved();
        userJpaRepository.save(entity);
    }
}
