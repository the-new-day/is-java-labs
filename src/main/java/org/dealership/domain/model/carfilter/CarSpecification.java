package org.dealership.domain.model.carfilter;

import org.dealership.domain.common.specification.Specification;
import org.dealership.domain.model.car.Car;

public interface CarSpecification extends Specification<Car> {
    void accept(CarSpecificationVisitor visitor);
}
