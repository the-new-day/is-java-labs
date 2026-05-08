package org.dealership.infrastructure.persistence.jpa.repository;

import org.dealership.domain.model.enums.ComponentType;
import org.dealership.infrastructure.persistence.jpa.entity.ComponentVariantJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ComponentVariantJpaRepository extends JpaRepository<ComponentVariantJpaEntity, UUID> {

    @Query("""
            SELECT DISTINCT v FROM ComponentVariantJpaEntity v
            LEFT JOIN FETCH v.compatibleModels cm
            LEFT JOIN FETCH cm.carModel
            WHERE v.id = :id AND v.removed = false
            """)
    Optional<ComponentVariantJpaEntity> findByIdAndRemovedFalse(@Param("id") UUID id);

    @Query("""
            SELECT DISTINCT v FROM ComponentVariantJpaEntity v
            LEFT JOIN FETCH v.compatibleModels cm
            LEFT JOIN FETCH cm.carModel
            WHERE v.removed = false
            """)
    List<ComponentVariantJpaEntity> findAllByRemovedFalse();

    @Query("""
            SELECT DISTINCT v FROM ComponentVariantJpaEntity v
            LEFT JOIN FETCH v.compatibleModels cm
            LEFT JOIN FETCH cm.carModel
            WHERE v.id IN :ids AND v.removed = false
            """)
    List<ComponentVariantJpaEntity> findAllByIdInAndRemovedFalse(@Param("ids") Collection<UUID> ids);

    @Query("""
            SELECT DISTINCT variant FROM ComponentVariantJpaEntity variant
            JOIN variant.compatibleModels compatibility
            WHERE variant.removed = false
              AND compatibility.removed = false
              AND compatibility.carModel.id = :modelId
            """)
    List<ComponentVariantJpaEntity> findAllCompatibleWithModel(@Param("modelId") UUID modelId);

    @Query("""
            SELECT DISTINCT variant FROM ComponentVariantJpaEntity variant
            JOIN variant.compatibleModels compatibility
            WHERE variant.removed = false
              AND compatibility.removed = false
              AND variant.componentType = :componentType
              AND compatibility.carModel.id = :modelId
            """)
    List<ComponentVariantJpaEntity> findAllByComponentTypeAndCompatibleModel(
            @Param("componentType") ComponentType componentType,
            @Param("modelId") UUID modelId
    );
}
