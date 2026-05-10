package org.dealership.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import org.dealership.domain.model.order.state.StockCarOrderStatus;

import java.util.UUID;

@Entity
@Table(name = "stock_car_orders")
public class StockCarOrderJpaEntity extends BaseJpaEntity {
    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Column(name = "manager_id", nullable = false)
    private UUID managerId;

    @Column(name = "car_id", nullable = false)
    private UUID carId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StockCarOrderStatus status;

    protected StockCarOrderJpaEntity() {
    }

    public StockCarOrderJpaEntity(
            UUID id,
            UUID clientId,
            UUID managerId,
            UUID carId,
            StockCarOrderStatus status
    ) {
        super(id);
        this.clientId = clientId;
        this.managerId = managerId;
        this.carId = carId;
        this.status = status;
    }

    public UUID getClientId() {
        return clientId;
    }

    public UUID getManagerId() {
        return managerId;
    }

    public UUID getCarId() {
        return carId;
    }

    public StockCarOrderStatus getStatus() {
        return status;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public void setManagerId(UUID managerId) {
        this.managerId = managerId;
    }

    public void setCarId(UUID carId) {
        this.carId = carId;
    }

    public void setStatus(StockCarOrderStatus status) {
        this.status = status;
    }
}
