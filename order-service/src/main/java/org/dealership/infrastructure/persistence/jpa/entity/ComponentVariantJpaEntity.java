package org.dealership.infrastructure.persistence.jpa.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.dealership.domain.model.enums.ComponentType;
import org.hibernate.annotations.BatchSize;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "component_variants")
@BatchSize(size = 32)
public class ComponentVariantJpaEntity extends BaseJpaEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "component_type", nullable = false)
    private ComponentType componentType;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surcharge", nullable = false, precision = 19, scale = 2)
    private BigDecimal surcharge;

    @OneToMany(mappedBy = "componentVariant", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 32)
    private Set<ComponentVariantCompatibilityJpaEntity> compatibleModels = new LinkedHashSet<>();

    protected ComponentVariantJpaEntity() {
    }

    public ComponentVariantJpaEntity(UUID id, ComponentType componentType, String name, BigDecimal surcharge) {
        super(id);
        this.componentType = componentType;
        this.name = name;
        this.surcharge = surcharge;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getSurcharge() {
        return surcharge;
    }

    public Set<ComponentVariantCompatibilityJpaEntity> getCompatibleModels() {
        return Collections.unmodifiableSet(compatibleModels);
    }

    public void addCompatibleModel(ComponentVariantCompatibilityJpaEntity compatibility) {
        compatibility.setComponentVariant(this);
        compatibleModels.add(compatibility);
    }

    public void replaceCompatibleModels(Set<ComponentVariantCompatibilityJpaEntity> newCompatibleModels) {
        compatibleModels.clear();
        newCompatibleModels.forEach(this::addCompatibleModel);
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurcharge(BigDecimal surcharge) {
        this.surcharge = surcharge;
    }
}
