package org.dealership.domain.model.carfilter.spec;

import org.dealership.domain.common.specification.Specification;
import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.enums.FuelType;

public record FuelTypeSpecification(FuelType type) implements Specification<Car> {
    @Override
    public boolean isSatisfiedBy(Car car) {
        return type == car.getModel().getFuelType();
    }
}
