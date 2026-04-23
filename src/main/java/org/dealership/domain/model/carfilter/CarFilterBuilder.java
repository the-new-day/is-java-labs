package org.dealership.domain.model.carfilter;

import org.dealership.domain.specification.Specification;
import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.carfilter.spec.*;
import org.dealership.domain.model.enums.*;
import org.dealership.domain.model.id.BrandId;
import org.dealership.domain.model.id.CarModelId;
import org.dealership.domain.model.vo.Money;

import java.util.ArrayList;
import java.util.List;

public class CarFilterBuilder {
    private final List<Specification<Car>> specs = new ArrayList<>();

    public CarFilter build() {
        if (specs.isEmpty()) {
            return null;
        }

        return new CarFilter(buildSpec());
    }

    public Specification<Car> buildSpec() {
        Specification<Car> result = specs.getFirst();
        for (int i = 1; i < specs.size(); i++) {
            result = result.and(specs.get(i));
        }
        return result;
    }

    public CarFilterBuilder priceRange(Money minPrice, Money maxPrice) {
        specs.add(new PriceRangeSpecification(minPrice, maxPrice));
        return this;
    }

    public CarFilterBuilder bodyType(CarBodyType type) {
        specs.add(new BodyTypeSpecification(type));
        return this;
    }

    public CarFilterBuilder driveType(DriveType type) {
        specs.add(new DriveTypeSpecification(type));
        return this;
    }

    public CarFilterBuilder fuelType(FuelType type) {
        specs.add(new FuelTypeSpecification(type));
        return this;
    }

    public CarFilterBuilder transmissionType(TransmissionType type) {
        specs.add(new TransmissionTypeSpecification(type));
        return this;
    }

    public CarFilterBuilder brand(BrandId id) {
        specs.add(new BrandSpecification(id));
        return this;
    }

    public CarFilterBuilder model(CarModelId id) {
        specs.add(new ModelSpecification(id));
        return this;
    }

    public CarFilterBuilder color(Color color) {
        specs.add(new ColorSpecification(color));
        return this;
    }

    public CarFilterBuilder enginePower(Integer minPower, Integer maxPower) {
        specs.add(new EnginePowerSpecification(minPower, maxPower));
        return this;
    }

    public CarFilterBuilder engineVolume(Double minVolume, Double maxVolume) {
        specs.add(new EngineVolumeSpecification(minVolume, maxVolume));
        return this;
    }
}
