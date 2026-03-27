package org.example.dealership.domain.model.carfilter.spec;

import org.example.dealership.domain.model.car.Car;
import org.example.dealership.domain.model.carfilter.CarSpecification;
import org.example.dealership.domain.model.carfilter.CarSpecificationVisitor;
import org.example.dealership.domain.model.id.BrandId;

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
