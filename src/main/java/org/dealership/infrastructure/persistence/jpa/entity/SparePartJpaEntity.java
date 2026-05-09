package org.dealership.infrastructure.persistence.jpa.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.BatchSize;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "spare_parts")
public class SparePartJpaEntity extends BaseJpaEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @OneToMany(mappedBy = "sparePart", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 32)
    private Set<SparePartCompatibilityJpaEntity> compatibleModels = new LinkedHashSet<>();

    protected SparePartJpaEntity() {
    }

    public SparePartJpaEntity(UUID id, String name, BigDecimal price) {
        super(id);
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Set<SparePartCompatibilityJpaEntity> getCompatibleModels() {
        return Collections.unmodifiableSet(compatibleModels);
    }

    public void addCompatibleModel(SparePartCompatibilityJpaEntity compatibility) {
        compatibility.setSparePart(this);
        compatibleModels.add(compatibility);
    }

    public void replaceCompatibleModels(Set<SparePartCompatibilityJpaEntity> newCompatibleModels) {
        compatibleModels.clear();
        newCompatibleModels.forEach(this::addCompatibleModel);
    }

    public void removeCompatibleModel(SparePartCompatibilityJpaEntity compatibility) {
        compatibleModels.remove(compatibility);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
