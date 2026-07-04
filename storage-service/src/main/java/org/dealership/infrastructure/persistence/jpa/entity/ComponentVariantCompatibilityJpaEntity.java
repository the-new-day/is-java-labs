package org.dealership.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "component_variant_compatible_models")
public class ComponentVariantCompatibilityJpaEntity extends BaseJpaEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "component_variant_id", nullable = false)
    private ComponentVariantJpaEntity componentVariant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_model_id", nullable = false)
    private CarModelJpaEntity carModel;

    protected ComponentVariantCompatibilityJpaEntity() {
    }

    public ComponentVariantCompatibilityJpaEntity(
            UUID id,
            ComponentVariantJpaEntity componentVariant,
            CarModelJpaEntity carModel
    ) {
        super(id);
        this.componentVariant = componentVariant;
        this.carModel = carModel;
    }

    public ComponentVariantJpaEntity getComponentVariant() {
        return componentVariant;
    }

    public CarModelJpaEntity getCarModel() {
        return carModel;
    }

    public void setComponentVariant(ComponentVariantJpaEntity componentVariant) {
        this.componentVariant = componentVariant;
    }

    public void setCarModel(CarModelJpaEntity carModel) {
        this.carModel = carModel;
    }
}
