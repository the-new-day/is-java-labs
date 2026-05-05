package org.dealership.infrastructure.persistence.jpa.repository;

import org.dealership.infrastructure.persistence.jpa.entity.TestDriveRequestJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TestDriveRequestJpaRepository extends JpaRepository<TestDriveRequestJpaEntity, UUID> {
    Optional<TestDriveRequestJpaEntity> findByIdAndRemovedFalse(UUID id);

    List<TestDriveRequestJpaEntity> findAllByRemovedFalse();

    List<TestDriveRequestJpaEntity> findAllByClientIdAndRemovedFalse(UUID clientId);

    List<TestDriveRequestJpaEntity> findAllByCarIdAndRemovedFalse(UUID carId);
}
