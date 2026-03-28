package org.dealership.domain.model.carfilter.spec;

import org.dealership.domain.common.specification.Specification;
import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.id.CarModelId;

public record ModelSpecification(CarModelId modelId) implements Specification<Car> {
    @Override
    public boolean isSatisfiedBy(Car car) {
        return modelId == car.getModel().getId();
    }
}
