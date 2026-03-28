package org.dealership.domain.model.carfilter.spec;

import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.carfilter.CarSpecification;
import org.dealership.domain.model.carfilter.CarSpecificationVisitor;
import org.dealership.domain.model.enums.DriveType;

public record DriveTypeSpecification(DriveType type) implements CarSpecification {
    @Override
    public boolean isSatisfiedBy(Car car) {
        return car.getModel().getDriveType() == type;
    }

    @Override
    public void accept(CarSpecificationVisitor visitor) {
        visitor.visit(this);
    }
}
