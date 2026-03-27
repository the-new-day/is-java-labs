package org.example.dealership.domain.model.carfilter.spec;

import org.example.dealership.domain.model.car.Car;
import org.example.dealership.domain.model.carfilter.CarSpecification;
import org.example.dealership.domain.model.carfilter.CarSpecificationVisitor;
import org.example.dealership.domain.model.enums.FuelType;

public record FuelTypeSpecification(FuelType type) implements CarSpecification {
    @Override
    public boolean isSatisfiedBy(Car car) {
        return type == car.getModel().getFuelType();
    }

    @Override
    public void accept(CarSpecificationVisitor visitor) {
        visitor.visit(this);
    }
}
