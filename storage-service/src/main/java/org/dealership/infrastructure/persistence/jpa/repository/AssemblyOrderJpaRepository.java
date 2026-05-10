package org.dealership.infrastructure.persistence.jpa.repository;

import org.dealership.infrastructure.persistence.jpa.entity.AssemblyOrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssemblyOrderJpaRepository extends JpaRepository<AssemblyOrderJpaEntity, UUID> {

    @Query("""
            SELECT DISTINCT ao FROM AssemblyOrderJpaEntity ao
            LEFT JOIN FETCH ao.requiredParts
            WHERE ao.id = :id AND ao.removed = false
            """)
    Optional<AssemblyOrderJpaEntity> findByIdAndRemovedFalse(@Param("id") UUID id);

    @Query("""
            SELECT DISTINCT ao FROM AssemblyOrderJpaEntity ao
            LEFT JOIN FETCH ao.requiredParts
            WHERE ao.removed = false
            """)
    List<AssemblyOrderJpaEntity> findAllByRemovedFalse();
}
