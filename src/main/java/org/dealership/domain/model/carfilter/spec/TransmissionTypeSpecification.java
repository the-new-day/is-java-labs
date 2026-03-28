package org.dealership.domain.model.carfilter.spec;

import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.carfilter.CarSpecification;
import org.dealership.domain.model.carfilter.CarSpecificationVisitor;
import org.dealership.domain.model.enums.TransmissionType;

public record TransmissionTypeSpecification(TransmissionType type) implements CarSpecification {
    @Override
    public boolean isSatisfiedBy(Car car) {
        TransmissionType carTransmissionType = car.getModel().getBaseTransmissionType();
        return carTransmissionType == type;
    }

    @Override
    public void accept(CarSpecificationVisitor visitor) {
        visitor.visit(this);
    }
}
