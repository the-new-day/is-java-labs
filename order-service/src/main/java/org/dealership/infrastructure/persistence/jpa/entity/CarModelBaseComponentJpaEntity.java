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
@Table(name = "car_model_base_component_variants")
public class CarModelBaseComponentJpaEntity extends BaseJpaEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_model_id", nullable = false)
    private CarModelJpaEntity carModel;

    @Enumerated(EnumType.STRING)
    @Column(name = "component_type", nullable = false)
    private ComponentType componentType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "component_variant_id", nullable = false)
    private ComponentVariantJpaEntity componentVariant;

    protected CarModelBaseComponentJpaEntity() {
    }

    public CarModelBaseComponentJpaEntity(
            UUID id,
            CarModelJpaEntity carModel,
            ComponentType componentType,
            ComponentVariantJpaEntity componentVariant
    ) {
        super(id);
        this.carModel = carModel;
        this.componentType = componentType;
        this.componentVariant = componentVariant;
    }

    public CarModelJpaEntity getCarModel() {
        return carModel;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public ComponentVariantJpaEntity getComponentVariant() {
        return componentVariant;
    }

    public void setCarModel(CarModelJpaEntity carModel) {
        this.carModel = carModel;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }

    public void setComponentVariant(ComponentVariantJpaEntity componentVariant) {
        this.componentVariant = componentVariant;
    }
}
