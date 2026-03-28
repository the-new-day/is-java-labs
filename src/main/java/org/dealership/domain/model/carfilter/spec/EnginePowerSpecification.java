package org.dealership.domain.model.carfilter.spec;

import org.dealership.domain.common.specification.Specification;
import org.dealership.domain.model.car.Car;

public record EnginePowerSpecification(Integer minPower, Integer maxPower) implements Specification<Car> {
    @Override
    public boolean isSatisfiedBy(Car car) {
        int power = car.getModel().getEnginePower();
        return (minPower == null || power >= minPower) &&
                (maxPower == null || power <= maxPower);
    }
}
