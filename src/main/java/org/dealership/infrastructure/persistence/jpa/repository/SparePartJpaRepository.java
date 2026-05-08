package org.dealership.infrastructure.persistence.jpa.repository;

import org.dealership.infrastructure.persistence.jpa.entity.SparePartJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SparePartJpaRepository extends JpaRepository<SparePartJpaEntity, UUID> {

    @Query("""
            SELECT DISTINCT sp FROM SparePartJpaEntity sp
            LEFT JOIN FETCH sp.compatibleModels cm
            LEFT JOIN FETCH cm.carModel
            WHERE sp.id = :id AND sp.removed = false
            """)
    Optional<SparePartJpaEntity> findByIdAndRemovedFalse(@Param("id") UUID id);

    @Query("""
            SELECT DISTINCT sp FROM SparePartJpaEntity sp
            LEFT JOIN FETCH sp.compatibleModels cm
            LEFT JOIN FETCH cm.carModel
            WHERE sp.removed = false
            """)
    List<SparePartJpaEntity> findAllByRemovedFalse();
}
