package org.dealership.infrastructure.persistence.jpa.repository;

import org.dealership.infrastructure.persistence.jpa.entity.CarModelJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarModelJpaRepository extends JpaRepository<CarModelJpaEntity, UUID> {
    Optional<CarModelJpaEntity> findByIdAndRemovedFalse(UUID id);

    List<CarModelJpaEntity> findAllByRemovedFalse();

    List<CarModelJpaEntity> findAllByBrandIdAndRemovedFalse(UUID brandId);
}
