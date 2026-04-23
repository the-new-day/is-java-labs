package org.dealership.domain.model.carfilter.spec;

import org.dealership.domain.specification.Specification;
import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.enums.TransmissionType;

public record TransmissionTypeSpecification(TransmissionType type) implements Specification<Car> {
    @Override
    public boolean isSatisfiedBy(Car car) {
        TransmissionType carTransmissionType = car.getModel().getBaseTransmissionType();
        return carTransmissionType == type;
    }
}
