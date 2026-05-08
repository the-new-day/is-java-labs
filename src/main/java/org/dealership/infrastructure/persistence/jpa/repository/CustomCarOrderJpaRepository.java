package org.dealership.infrastructure.persistence.jpa.repository;

import org.dealership.infrastructure.persistence.jpa.entity.CustomCarOrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomCarOrderJpaRepository extends JpaRepository<CustomCarOrderJpaEntity, UUID> {

    @Query("""
            SELECT o FROM CustomCarOrderJpaEntity o
            JOIN FETCH o.configuration cfg
            JOIN FETCH cfg.carModel cm
            JOIN FETCH cm.brand
            WHERE o.id = :id AND o.removed = false
            """)
    Optional<CustomCarOrderJpaEntity> findByIdAndRemovedFalse(@Param("id") UUID id);

    @Query("""
            SELECT o FROM CustomCarOrderJpaEntity o
            JOIN FETCH o.configuration cfg
            JOIN FETCH cfg.carModel cm
            JOIN FETCH cm.brand
            WHERE o.removed = false
            """)
    List<CustomCarOrderJpaEntity> findAllByRemovedFalse();

    @Query("""
            SELECT o FROM CustomCarOrderJpaEntity o
            JOIN FETCH o.configuration cfg
            JOIN FETCH cfg.carModel cm
            JOIN FETCH cm.brand
            WHERE o.clientId = :clientId AND o.removed = false
            """)
    List<CustomCarOrderJpaEntity> findAllByClientIdAndRemovedFalse(@Param("clientId") UUID clientId);

    @Query("""
            SELECT o FROM CustomCarOrderJpaEntity o
            JOIN FETCH o.configuration cfg
            JOIN FETCH cfg.carModel cm
            JOIN FETCH cm.brand
            WHERE o.managerId = :managerId AND o.removed = false
            """)
    List<CustomCarOrderJpaEntity> findAllByManagerIdAndRemovedFalse(@Param("managerId") UUID managerId);
}
