package org.example.dealership.domain.model.carfilter.spec;

import org.example.dealership.domain.model.car.Car;
import org.example.dealership.domain.model.carfilter.CarSpecification;
import org.example.dealership.domain.model.carfilter.CarSpecificationVisitor;
import org.example.dealership.domain.model.id.CarModelId;

public record ModelSpecification(CarModelId modelId) implements CarSpecification {
    @Override
    public boolean isSatisfiedBy(Car car) {
        return modelId == car.getModel().getId();
    }

    @Override
    public void accept(CarSpecificationVisitor visitor) {
        visitor.visit(this);
    }
}
