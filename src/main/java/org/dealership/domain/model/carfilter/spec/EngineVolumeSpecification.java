package org.dealership.domain.model.carfilter.spec;

import org.dealership.domain.specification.Specification;
import org.dealership.domain.model.car.Car;

public record EngineVolumeSpecification(Double minVolume, Double maxVolume) implements Specification<Car> {
    @Override
    public boolean isSatisfiedBy(Car car) {
        double volume = car.getModel().getEngineVolume();
        return (minVolume == null || volume >= minVolume) &&
                (maxVolume == null || volume <= maxVolume);
    }
}
