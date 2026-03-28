package org.dealership.domain.model.carfilter.spec;

import org.dealership.domain.common.specification.Specification;
import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.enums.DriveType;

public record DriveTypeSpecification(DriveType type) implements Specification<Car> {
    @Override
    public boolean isSatisfiedBy(Car car) {
        return car.getModel().getDriveType() == type;
    }
}
