package org.dealership.infrastructure.persistence.jpa.repository;

import org.dealership.infrastructure.persistence.jpa.entity.CarModelJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarModelJpaRepository extends JpaRepository<CarModelJpaEntity, UUID> {

    @Query("""
            SELECT cm FROM CarModelJpaEntity cm
            JOIN FETCH cm.brand
            WHERE cm.id = :id AND cm.removed = false
            """)
    Optional<CarModelJpaEntity> findByIdAndRemovedFalse(@Param("id") UUID id);

    @Query("""
            SELECT cm FROM CarModelJpaEntity cm
            JOIN FETCH cm.brand
            WHERE cm.removed = false
            """)
    List<CarModelJpaEntity> findAllByRemovedFalse();

    @Query("""
            SELECT cm FROM CarModelJpaEntity cm
            JOIN FETCH cm.brand b
            WHERE b.id = :brandId AND cm.removed = false
            """)
    List<CarModelJpaEntity> findAllByBrandIdAndRemovedFalse(@Param("brandId") UUID brandId);

    @Query("""
            SELECT cm FROM CarModelJpaEntity cm
            JOIN FETCH cm.brand
            WHERE cm.id IN :ids AND cm.removed = false
            """)
    List<CarModelJpaEntity> findAllByIdInAndRemovedFalse(@Param("ids") Collection<UUID> ids);
}
