package org.dealership.infrastructure.persistence.jpa.repository;

import org.dealership.domain.model.enums.ComponentType;
import org.dealership.infrastructure.persistence.jpa.entity.ComponentVariantJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ComponentVariantJpaRepository extends JpaRepository<ComponentVariantJpaEntity, UUID> {
    Optional<ComponentVariantJpaEntity> findByIdAndRemovedFalse(UUID id);

    List<ComponentVariantJpaEntity> findAllByRemovedFalse();

    @Query("""
            select distinct variant
            from ComponentVariantJpaEntity variant
            join variant.compatibleModels compatibility
            where variant.removed = false
              and compatibility.removed = false
              and compatibility.carModel.id = :modelId
            """)
    List<ComponentVariantJpaEntity> findAllCompatibleWithModel(
            @Param("modelId") UUID modelId
    );

    @Query("""
            select distinct variant
            from ComponentVariantJpaEntity variant
            join variant.compatibleModels compatibility
            where variant.removed = false
              and compatibility.removed = false
              and variant.componentType = :componentType
              and compatibility.carModel.id = :modelId
            """)
    List<ComponentVariantJpaEntity> findAllByComponentTypeAndCompatibleModel(
            @Param("componentType") ComponentType componentType,
            @Param("modelId") UUID modelId
    );
}
