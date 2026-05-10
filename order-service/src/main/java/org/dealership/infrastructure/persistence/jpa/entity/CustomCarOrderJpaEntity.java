package org.dealership.infrastructure.persistence.jpa.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.dealership.domain.model.order.state.CustomCarOrderStatus;

import java.util.UUID;

@Entity
@Table(name = "custom_car_orders")
public class CustomCarOrderJpaEntity extends BaseJpaEntity {
    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Column(name = "manager_id", nullable = false)
    private UUID managerId;

    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "configuration_id", nullable = false)
    private ConfigurationJpaEntity configuration;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CustomCarOrderStatus status;

    protected CustomCarOrderJpaEntity() {
    }

    public CustomCarOrderJpaEntity(
            UUID id,
            UUID clientId,
            UUID managerId,
            ConfigurationJpaEntity configuration,
            CustomCarOrderStatus status
    ) {
        super(id);
        this.clientId = clientId;
        this.managerId = managerId;
        this.configuration = configuration;
        this.status = status;
    }

    public UUID getClientId() {
        return clientId;
    }

    public UUID getManagerId() {
        return managerId;
    }

    public ConfigurationJpaEntity getConfiguration() {
        return configuration;
    }

    public CustomCarOrderStatus getStatus() {
        return status;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public void setManagerId(UUID managerId) {
        this.managerId = managerId;
    }

    public void setConfiguration(ConfigurationJpaEntity configuration) {
        this.configuration = configuration;
    }

    public void setStatus(CustomCarOrderStatus status) {
        this.status = status;
    }
}
