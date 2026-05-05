package org.dealership.infrastructure.persistence.jpa.repository;

import org.dealership.infrastructure.persistence.jpa.entity.SparePartJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SparePartJpaRepository extends JpaRepository<SparePartJpaEntity, UUID> {
    Optional<SparePartJpaEntity> findByIdAndRemovedFalse(UUID id);

    List<SparePartJpaEntity> findAllByRemovedFalse();
}
