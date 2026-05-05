package org.dealership.infrastructure.persistence.jpa.repository;

import org.dealership.infrastructure.persistence.jpa.entity.CustomCarOrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomCarOrderJpaRepository extends JpaRepository<CustomCarOrderJpaEntity, UUID> {
    Optional<CustomCarOrderJpaEntity> findByIdAndRemovedFalse(UUID id);

    List<CustomCarOrderJpaEntity> findAllByRemovedFalse();

    List<CustomCarOrderJpaEntity> findAllByClientIdAndRemovedFalse(UUID clientId);

    List<CustomCarOrderJpaEntity> findAllByManagerIdAndRemovedFalse(UUID managerId);
}
