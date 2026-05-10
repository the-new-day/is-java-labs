package org.dealership.infrastructure.persistence.jpa.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.dealership.domain.model.enums.CarBodyType;
import org.dealership.domain.model.enums.DriveType;
import org.dealership.domain.model.enums.FuelType;
import org.dealership.domain.model.enums.TransmissionType;
import org.hibernate.annotations.BatchSize;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "car_models")
@BatchSize(size = 32)
public class CarModelJpaEntity extends BaseJpaEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private BrandJpaEntity brand;

    @Column(name = "model_name", nullable = false)
    private String modelName;

    @Column(name = "base_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal basePrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "body_type", nullable = false)
    private CarBodyType bodyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type", nullable = false)
    private FuelType fuelType;

    @Enumerated(EnumType.STRING)
    @Column(name = "drive_type", nullable = false)
    private DriveType driveType;

    @Column(name = "engine_volume", nullable = false)
    private double engineVolume;

    @Column(name = "engine_power", nullable = false)
    private int enginePower;

    @Enumerated(EnumType.STRING)
    @Column(name = "base_transmission_type", nullable = false)
    private TransmissionType baseTransmissionType;

    @OneToMany(mappedBy = "carModel", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 32)
    private Set<CarModelBaseComponentJpaEntity> baseComponents = new LinkedHashSet<>();

    @OneToMany(mappedBy = "carModel", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 32)
    private Set<CarModelConfigurableComponentJpaEntity> configurableComponents = new LinkedHashSet<>();

    protected CarModelJpaEntity() {
    }

    public CarModelJpaEntity(
            UUID id,
            BrandJpaEntity brand,
            String modelName,
            BigDecimal basePrice,
            CarBodyType bodyType,
            FuelType fuelType,
            DriveType driveType,
            double engineVolume,
            int enginePower,
            TransmissionType baseTransmissionType
    ) {
        super(id);
        this.brand = brand;
        this.modelName = modelName;
        this.basePrice = basePrice;
        this.bodyType = bodyType;
        this.fuelType = fuelType;
        this.driveType = driveType;
        this.engineVolume = engineVolume;
        this.enginePower = enginePower;
        this.baseTransmissionType = baseTransmissionType;
    }

    public BrandJpaEntity getBrand() {
        return brand;
    }

    public String getModelName() {
        return modelName;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public CarBodyType getBodyType() {
        return bodyType;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public DriveType getDriveType() {
        return driveType;
    }

    public double getEngineVolume() {
        return engineVolume;
    }

    public int getEnginePower() {
        return enginePower;
    }

    public TransmissionType getBaseTransmissionType() {
        return baseTransmissionType;
    }

    public Set<CarModelBaseComponentJpaEntity> getBaseComponents() {
        return Collections.unmodifiableSet(baseComponents);
    }

    public Set<CarModelConfigurableComponentJpaEntity> getConfigurableComponents() {
        return Collections.unmodifiableSet(configurableComponents);
    }

    public void addBaseComponent(CarModelBaseComponentJpaEntity baseComponent) {
        baseComponent.setCarModel(this);
        baseComponents.add(baseComponent);
    }

    public void replaceBaseComponents(Set<CarModelBaseComponentJpaEntity> newBaseComponents) {
        baseComponents.clear();
        newBaseComponents.forEach(this::addBaseComponent);
    }

    public void addConfigurableComponent(CarModelConfigurableComponentJpaEntity configurableComponent) {
        configurableComponent.setCarModel(this);
        configurableComponents.add(configurableComponent);
    }

    public void replaceConfigurableComponents(Set<CarModelConfigurableComponentJpaEntity> newConfigurableComponents) {
        configurableComponents.clear();
        newConfigurableComponents.forEach(this::addConfigurableComponent);
    }

    public void setBrand(BrandJpaEntity brand) {
        this.brand = brand;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public void setBodyType(CarBodyType bodyType) {
        this.bodyType = bodyType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public void setDriveType(DriveType driveType) {
        this.driveType = driveType;
    }

    public void setEngineVolume(double engineVolume) {
        this.engineVolume = engineVolume;
    }

    public void setEnginePower(int enginePower) {
        this.enginePower = enginePower;
    }

    public void setBaseTransmissionType(TransmissionType baseTransmissionType) {
        this.baseTransmissionType = baseTransmissionType;
    }
}
