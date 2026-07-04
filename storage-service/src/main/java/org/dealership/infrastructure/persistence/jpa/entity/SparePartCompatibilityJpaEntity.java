package org.dealership.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "spare_part_compatible_models")
public class SparePartCompatibilityJpaEntity extends BaseJpaEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "spare_part_id", nullable = false)
    private SparePartJpaEntity sparePart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_model_id", nullable = false)
    private CarModelJpaEntity carModel;

    protected SparePartCompatibilityJpaEntity() {
    }

    public SparePartCompatibilityJpaEntity(UUID id, SparePartJpaEntity sparePart, CarModelJpaEntity carModel) {
        super(id);
        this.sparePart = sparePart;
        this.carModel = carModel;
    }

    public SparePartJpaEntity getSparePart() {
        return sparePart;
    }

    public CarModelJpaEntity getCarModel() {
        return carModel;
    }

    public void setSparePart(SparePartJpaEntity sparePart) {
        this.sparePart = sparePart;
    }

    public void setCarModel(CarModelJpaEntity carModel) {
        this.carModel = carModel;
    }
}
