package org.example.dealership.domain.model.carfilter;

import org.example.dealership.domain.common.specification.Specification;
import org.example.dealership.domain.model.car.Car;

public interface CarSpecification extends Specification<Car> {
    void accept(CarSpecificationVisitor visitor);
}
