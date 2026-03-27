package org.example.dealership.domain.model.carfilter;

import org.example.dealership.domain.model.car.Car;

import java.util.List;

public class CarFilter {
    private final List<CarSpecification> specs;

    public CarFilter(List<CarSpecification> specs) {
        this.specs = specs;
    }

    public boolean matches(Car car) {
        return specs.stream()
                .allMatch(spec -> spec.isSatisfiedBy(car));
    }
}
