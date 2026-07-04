package org.dealership.domain.model.configuration;

import org.dealership.domain.model.enums.ComponentType;
import org.dealership.domain.model.id.CarModelId;
import org.dealership.domain.model.id.ComponentVariantId;
import org.dealership.domain.model.vo.Money;
import org.dealership.domain.validation.DomainChecks;

import java.util.Set;

public class ComponentVariant {
    private final ComponentVariantId id;
    private final ComponentType componentType;
    private final String name;
    private final Money surcharge;
    private final Set<CarModelId> compatibleModelIds;

    public ComponentVariant(
            ComponentVariantId id,
            ComponentType componentType,
            String name,
            Money surcharge,
            Set<CarModelId> compatibleModelIds
    ) {
        this.id = DomainChecks.notNull(id, "variantId");
        this.componentType = DomainChecks.notNull(componentType, "componentType");
        this.name = DomainChecks.notBlank(name, "variantName");
        this.surcharge = DomainChecks.notNull(surcharge, "surcharge");
        this.compatibleModelIds = Set.copyOf(DomainChecks.notEmpty(compatibleModelIds, "compatibleModelIds"));
    }

    public boolean isCompatibleWith(CarModelId modelId) {
        return compatibleModelIds.contains(modelId);
    }

    public ComponentVariantId getId() {
        return id;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public String getName() {
        return name;
    }

    public Money getSurcharge() {
        return surcharge;
    }

    public Set<CarModelId> getCompatibleModelIds() {
        return Set.copyOf(compatibleModelIds);
    }
}
