package org.dealership.infrastructure.persistence.jpa.repository;

import org.dealership.infrastructure.persistence.jpa.entity.BrandJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BrandJpaRepository extends JpaRepository<BrandJpaEntity, UUID> {
    Optional<BrandJpaEntity> findByIdAndRemovedFalse(UUID id);

    List<BrandJpaEntity> findAllByRemovedFalse();

    Optional<BrandJpaEntity> findByNameAndRemovedFalse(String name);
}
