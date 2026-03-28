package org.dealership.domain.model.carfilter.spec;

import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.carfilter.CarSpecification;
import org.dealership.domain.model.carfilter.CarSpecificationVisitor;
import org.dealership.domain.model.id.BrandId;

public record BrandSpecification(BrandId brandId) implements CarSpecification {
    @Override
    public boolean isSatisfiedBy(Car car) {
        return brandId == car.getModel().getBrand().getId();
    }

    @Override
    public void accept(CarSpecificationVisitor visitor) {
        visitor.visit(this);
    }
}
