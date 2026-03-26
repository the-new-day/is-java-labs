package org.example.dealership.domain.model.car;

import org.example.dealership.domain.model.id.BrandId;
import org.example.dealership.domain.validation.DomainChecks;

public class Brand {
    private final BrandId id;
    private final String name;

    public Brand(BrandId id, String name) {
        this.id = DomainChecks.notNull(id, "brandId");
        this.name = DomainChecks.notBlank(name, "brandName");
    }

    public BrandId getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
