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
import org.dealership.domain.model.enums.Color;

import java.util.UUID;

@Entity
@Table(name = "cars")
public class CarJpaEntity extends BaseJpaEntity {
    @Column(name = "vin", nullable = false, unique = true, length = 17)
    private String vin;

    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "configuration_id", nullable = false)
    private ConfigurationJpaEntity configuration;

    @Enumerated(EnumType.STRING)
    @Column(name = "color", nullable = false)
    private Color color;

    @Column(name = "test_drive_available", nullable = false)
    private boolean testDriveAvailable;

    protected CarJpaEntity() {
    }

    public CarJpaEntity(
            UUID id,
            String vin,
            ConfigurationJpaEntity configuration,
            Color color,
            boolean testDriveAvailable
    ) {
        super(id);
        this.vin = vin;
        this.configuration = configuration;
        this.color = color;
        this.testDriveAvailable = testDriveAvailable;
    }

    public String getVin() {
        return vin;
    }

    public ConfigurationJpaEntity getConfiguration() {
        return configuration;
    }

    public Color getColor() {
        return color;
    }

    public boolean isTestDriveAvailable() {
        return testDriveAvailable;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setConfiguration(ConfigurationJpaEntity configuration) {
        this.configuration = configuration;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setTestDriveAvailable(boolean testDriveAvailable) {
        this.testDriveAvailable = testDriveAvailable;
    }
}
