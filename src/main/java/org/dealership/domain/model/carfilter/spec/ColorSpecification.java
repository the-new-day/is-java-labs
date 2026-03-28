package org.dealership.domain.model.carfilter.spec;

import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.carfilter.CarSpecification;
import org.dealership.domain.model.carfilter.CarSpecificationVisitor;
import org.dealership.domain.model.enums.Color;

public record ColorSpecification(Color color) implements CarSpecification {
    @Override
    public boolean isSatisfiedBy(Car car) {
        return car.getColor() == color;
    }

    @Override
    public void accept(CarSpecificationVisitor visitor) {
        visitor.visit(this);
    }
}
