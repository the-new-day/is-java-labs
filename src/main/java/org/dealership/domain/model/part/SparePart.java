package org.dealership.domain.model.part;

import org.dealership.domain.model.id.CarModelId;
import org.dealership.domain.model.id.SparePartId;
import org.dealership.domain.model.vo.Money;
import org.dealership.domain.validation.DomainChecks;

import java.util.Set;

public class SparePart {
    private final SparePartId id;
    private final String name;
    private final Money price;
    private final Set<CarModelId> compatibleModelIds;

    public SparePart(SparePartId id, String name, Money price, Set<CarModelId> compatibleModelIds) {
        this.id = DomainChecks.notNull(id, "partId");
        this.name = DomainChecks.notBlank(name, "partName");
        this.price = DomainChecks.notNull(price, "price");
        this.compatibleModelIds = Set.copyOf(DomainChecks.notEmpty(compatibleModelIds, "compatibleModelIds"));
    }

    public SparePartId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public Set<CarModelId> getCompatibleModelIds() {
        return Set.copyOf(compatibleModelIds);
    }

    public boolean isCompatibleWith(CarModelId modelId) {
        return compatibleModelIds.contains(modelId);
    }
}
