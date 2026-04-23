package org.dealership.infrastructure.persistence.jpa.repository;

import org.dealership.domain.model.user.UserRole;
import org.dealership.infrastructure.persistence.jpa.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, UUID> {
    Optional<UserJpaEntity> findByIdAndRemovedFalse(UUID id);

    List<UserJpaEntity> findAllByRemovedFalse();

    List<UserJpaEntity> findAllByRoleAndRemovedFalse(UserRole role);
}
