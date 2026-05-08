package org.dealership.infrastructure.persistence.jpa.repository;

import org.dealership.infrastructure.persistence.jpa.entity.CarJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarJpaRepository extends JpaRepository<CarJpaEntity, UUID>, JpaSpecificationExecutor<CarJpaEntity> {

    @Query("""
            SELECT c FROM CarJpaEntity c
            JOIN FETCH c.configuration cfg
            JOIN FETCH cfg.carModel cm
            JOIN FETCH cm.brand
            WHERE c.id = :id AND c.removed = false
            """)
    Optional<CarJpaEntity> findByIdAndRemovedFalse(@Param("id") UUID id);

    @Query("""
            SELECT c FROM CarJpaEntity c
            JOIN FETCH c.configuration cfg
            JOIN FETCH cfg.carModel cm
            JOIN FETCH cm.brand
            WHERE c.vin = :vin AND c.removed = false
            """)
    Optional<CarJpaEntity> findByVinAndRemovedFalse(@Param("vin") String vin);

    @Query("""
            SELECT c FROM CarJpaEntity c
            JOIN FETCH c.configuration cfg
            JOIN FETCH cfg.carModel cm
            JOIN FETCH cm.brand
            WHERE c.removed = false
            """)
    List<CarJpaEntity> findAllByRemovedFalse();

    @Query("""
            SELECT c FROM CarJpaEntity c
            JOIN FETCH c.configuration cfg
            JOIN FETCH cfg.carModel cm
            JOIN FETCH cm.brand
            WHERE c.testDriveAvailable = true AND c.removed = false
            """)
    List<CarJpaEntity> findAllByTestDriveAvailableTrueAndRemovedFalse();
}
