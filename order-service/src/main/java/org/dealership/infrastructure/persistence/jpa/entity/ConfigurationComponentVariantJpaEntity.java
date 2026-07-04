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
@Table(name = "configuration_component_variants")
public class ConfigurationComponentVariantJpaEntity extends BaseJpaEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "configuration_id", nullable = false)
    private ConfigurationJpaEntity configuration;

    @Enumerated(EnumType.STRING)
    @Column(name = "component_type", nullable = false)
    private ComponentType componentType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "component_variant_id", nullable = false)
    private ComponentVariantJpaEntity componentVariant;

    protected ConfigurationComponentVariantJpaEntity() {
    }

    public ConfigurationComponentVariantJpaEntity(
            UUID id,
            ConfigurationJpaEntity configuration,
            ComponentType componentType,
            ComponentVariantJpaEntity componentVariant
    ) {
        super(id);
        this.configuration = configuration;
        this.componentType = componentType;
        this.componentVariant = componentVariant;
    }

    public ConfigurationJpaEntity getConfiguration() {
        return configuration;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public ComponentVariantJpaEntity getComponentVariant() {
        return componentVariant;
    }

    public void setConfiguration(ConfigurationJpaEntity configuration) {
        this.configuration = configuration;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }

    public void setComponentVariant(ComponentVariantJpaEntity componentVariant) {
        this.componentVariant = componentVariant;
    }
}
