package org.dealership.domain.model.carfilter.spec;

import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.carfilter.CarSpecification;
import org.dealership.domain.model.carfilter.CarSpecificationVisitor;

public record EngineVolumeSpecification(Double minVolume, Double maxVolume) implements CarSpecification {
    @Override
    public boolean isSatisfiedBy(Car car) {
        double volume = car.getModel().getEngineVolume();
        return (minVolume == null || volume >= minVolume) &&
                (maxVolume == null || volume <= maxVolume);
    }

    @Override
    public void accept(CarSpecificationVisitor visitor) {
        visitor.visit(this);
    }
}
