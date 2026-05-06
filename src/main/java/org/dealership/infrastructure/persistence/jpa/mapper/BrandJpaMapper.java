package org.dealership.infrastructure.persistence.jpa.mapper;

import org.dealership.domain.model.car.Brand;
import org.dealership.domain.model.id.BrandId;
import org.dealership.infrastructure.persistence.jpa.entity.BrandJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class BrandJpaMapper {
    public Brand toDomain(BrandJpaEntity entity) {
        return new Brand(new BrandId(entity.getId()), entity.getName());
    }

    public BrandJpaEntity toEntity(Brand brand) {
        return new BrandJpaEntity(brand.getId().value(), brand.getName());
    }
}
