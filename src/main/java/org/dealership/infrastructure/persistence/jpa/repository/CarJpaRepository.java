package org.dealership.infrastructure.persistence.jpa.repository;

import org.dealership.infrastructure.persistence.jpa.entity.CarJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarJpaRepository extends JpaRepository<CarJpaEntity, UUID>, JpaSpecificationExecutor<CarJpaEntity> {
    Optional<CarJpaEntity> findByIdAndRemovedFalse(UUID id);

    Optional<CarJpaEntity> findByVinAndRemovedFalse(String vin);

    List<CarJpaEntity> findAllByRemovedFalse();

    List<CarJpaEntity> findAllByTestDriveAvailableTrueAndRemovedFalse();
}
