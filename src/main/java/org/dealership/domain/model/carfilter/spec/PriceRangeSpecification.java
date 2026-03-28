package org.dealership.domain.model.carfilter.spec;

import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.carfilter.CarSpecification;
import org.dealership.domain.model.carfilter.CarSpecificationVisitor;
import org.dealership.domain.model.vo.Money;

public record PriceRangeSpecification(Money minPrice, Money maxPrice) implements CarSpecification {
    @Override
    public boolean isSatisfiedBy(Car car) {
        Money price = car.getPrice();
        return (minPrice == null || price.compareTo(minPrice) >= 0) &&
               (maxPrice == null || price.compareTo(maxPrice) <= 0);
    }

    @Override
    public void accept(CarSpecificationVisitor visitor) {
        visitor.visit(this);
    }
}
