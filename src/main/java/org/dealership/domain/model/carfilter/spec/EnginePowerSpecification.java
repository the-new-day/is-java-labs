package org.dealership.domain.model.carfilter.spec;

import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.carfilter.CarSpecification;
import org.dealership.domain.model.carfilter.CarSpecificationVisitor;

public record EnginePowerSpecification(Integer minPower, Integer maxPower) implements CarSpecification {
    @Override
    public boolean isSatisfiedBy(Car car) {
        int power = car.getModel().getEnginePower();
        return (minPower == null || power >= minPower) &&
                (maxPower == null || power <= maxPower);
    }

    @Override
    public void accept(CarSpecificationVisitor visitor) {
        visitor.visit(this);
    }
}
