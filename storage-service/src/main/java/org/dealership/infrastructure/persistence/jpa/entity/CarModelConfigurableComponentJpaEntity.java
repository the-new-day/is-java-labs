package org.dealership.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.dealership.domain.model.enums.ComponentType;

import java.util.UUID;

@Entity
@Table(name = "car_model_configurable_component_types")
public class CarModelConfigurableComponentJpaEntity extends BaseJpaEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_model_id", nullable = false)
    private CarModelJpaEntity carModel;

    @Enumerated(EnumType.STRING)
    @Column(name = "component_type", nullable = false)
    private ComponentType componentType;

    protected CarModelConfigurableComponentJpaEntity() {
    }

    public CarModelConfigurableComponentJpaEntity(UUID id, CarModelJpaEntity carModel, ComponentType componentType) {
        super(id);
        this.carModel = carModel;
        this.componentType = componentType;
    }

    public CarModelJpaEntity getCarModel() {
        return carModel;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public void setCarModel(CarModelJpaEntity carModel) {
        this.carModel = carModel;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }
}
