package org.dealership.infrastructure.persistence.jpa.repository;

import org.dealership.infrastructure.persistence.jpa.entity.ConfigurationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConfigurationJpaRepository extends JpaRepository<ConfigurationJpaEntity, UUID> {

    @Query("""
            SELECT cfg FROM ConfigurationJpaEntity cfg
            JOIN FETCH cfg.carModel cm
            JOIN FETCH cm.brand
            WHERE cfg.id = :id AND cfg.removed = false
            """)
    Optional<ConfigurationJpaEntity> findByIdAndRemovedFalse(@Param("id") UUID id);

    @Query("""
            SELECT cfg FROM ConfigurationJpaEntity cfg
            JOIN FETCH cfg.carModel cm
            JOIN FETCH cm.brand
            WHERE cfg.removed = false
            """)
    List<ConfigurationJpaEntity> findAllByRemovedFalse();

    @Query("""
            SELECT cfg FROM ConfigurationJpaEntity cfg
            JOIN FETCH cfg.carModel cm
            JOIN FETCH cm.brand
            WHERE cm.id = :carModelId AND cfg.removed = false
            """)
    List<ConfigurationJpaEntity> findAllByCarModelIdAndRemovedFalse(@Param("carModelId") UUID carModelId);
}
