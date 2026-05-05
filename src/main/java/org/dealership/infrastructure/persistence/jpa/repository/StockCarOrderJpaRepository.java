package org.dealership.infrastructure.persistence.jpa.repository;

import org.dealership.infrastructure.persistence.jpa.entity.StockCarOrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StockCarOrderJpaRepository extends JpaRepository<StockCarOrderJpaEntity, UUID> {
    Optional<StockCarOrderJpaEntity> findByIdAndRemovedFalse(UUID id);

    List<StockCarOrderJpaEntity> findAllByRemovedFalse();

    List<StockCarOrderJpaEntity> findAllByClientIdAndRemovedFalse(UUID clientId);

    List<StockCarOrderJpaEntity> findAllByManagerIdAndRemovedFalse(UUID managerId);
}
