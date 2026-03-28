package org.dealership.application.port.out.persistence;

import org.dealership.domain.common.specification.Specification;
import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.id.CarId;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    CarId nextId();
    void save(Car car);
    Optional<Car> findById(CarId id);
    List<Car> findAll();
    List<Car> findBySpec(Specification<Car> spec);
    void deleteById(CarId id);
}
