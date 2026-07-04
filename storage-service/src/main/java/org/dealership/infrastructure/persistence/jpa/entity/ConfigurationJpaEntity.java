package org.dealership.infrastructure.persistence.jpa.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.BatchSize;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "configurations")
@BatchSize(size = 32)
public class ConfigurationJpaEntity extends BaseJpaEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_model_id", nullable = false)
    private CarModelJpaEntity carModel;

    @OneToMany(mappedBy = "configuration", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 32)
    private Set<ConfigurationComponentVariantJpaEntity> componentVariants = new LinkedHashSet<>();

    protected ConfigurationJpaEntity() {
    }

    public ConfigurationJpaEntity(UUID id, CarModelJpaEntity carModel) {
        super(id);
        this.carModel = carModel;
    }

    public CarModelJpaEntity getCarModel() {
        return carModel;
    }

    public Set<ConfigurationComponentVariantJpaEntity> getComponentVariants() {
        return Collections.unmodifiableSet(componentVariants);
    }

    public void addComponentVariant(ConfigurationComponentVariantJpaEntity componentVariant) {
        componentVariant.setConfiguration(this);
        componentVariants.add(componentVariant);
    }

    public void replaceComponentVariants(Set<ConfigurationComponentVariantJpaEntity> newComponentVariants) {
        componentVariants.clear();
        newComponentVariants.forEach(this::addComponentVariant);
    }

    public void removeComponentVariant(ConfigurationComponentVariantJpaEntity componentVariant) {
        componentVariants.remove(componentVariant);
    }

    public void setCarModel(CarModelJpaEntity carModel) {
        this.carModel = carModel;
    }
}
