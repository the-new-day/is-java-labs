package org.dealership.domain.model.carfilter.spec;

import org.dealership.domain.specification.Specification;
import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.id.BrandId;

public record BrandSpecification(BrandId brandId) implements Specification<Car> {
    @Override
    public boolean isSatisfiedBy(Car car) {
        return brandId == car.getModel().getBrand().getId();
    }
}
