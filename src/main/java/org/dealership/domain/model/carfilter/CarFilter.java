package org.dealership.domain.model.carfilter;

import org.dealership.domain.common.specification.Specification;
import org.dealership.domain.model.car.Car;

import java.util.List;

public class CarFilter {
    private final Specification<Car> spec;

    public CarFilter(Specification<Car> spec) {
        this.spec = spec;
    }

    public static CarFilterBuilder builder() {
        return new CarFilterBuilder();
    }

    public boolean matches(Car car) {
        return spec.isSatisfiedBy(car);
    }
}
