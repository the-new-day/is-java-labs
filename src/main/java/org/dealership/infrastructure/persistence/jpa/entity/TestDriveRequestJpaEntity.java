package org.dealership.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "test_drive_requests")
public class TestDriveRequestJpaEntity extends BaseJpaEntity {
    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Column(name = "car_id", nullable = false)
    private UUID carId;

    @Column(name = "starts_at", nullable = false)
    private LocalDateTime startsAt;

    protected TestDriveRequestJpaEntity() {
    }

    public TestDriveRequestJpaEntity(UUID id, UUID clientId, UUID carId, LocalDateTime startsAt) {
        super(id);
        this.clientId = clientId;
        this.carId = carId;
        this.startsAt = startsAt;
    }

    public UUID getClientId() {
        return clientId;
    }

    public UUID getCarId() {
        return carId;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public void setCarId(UUID carId) {
        this.carId = carId;
    }

    public void setStartsAt(LocalDateTime startsAt) {
        this.startsAt = startsAt;
    }
}
