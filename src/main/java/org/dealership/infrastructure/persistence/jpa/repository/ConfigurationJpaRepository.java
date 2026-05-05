package org.dealership.infrastructure.persistence.jpa.repository;

import org.dealership.infrastructure.persistence.jpa.entity.ConfigurationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConfigurationJpaRepository extends JpaRepository<ConfigurationJpaEntity, UUID> {
    Optional<ConfigurationJpaEntity> findByIdAndRemovedFalse(UUID id);

    List<ConfigurationJpaEntity> findAllByRemovedFalse();

    List<ConfigurationJpaEntity> findAllByCarModelIdAndRemovedFalse(UUID carModelId);
}
