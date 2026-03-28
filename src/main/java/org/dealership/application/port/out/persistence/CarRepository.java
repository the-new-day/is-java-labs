package org.dealership.application.port.out.persistence;

import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.carfilter.CarFilter;
import org.dealership.domain.model.id.CarId;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    CarId nextId();
    void save(Car car);
    Optional<Car> findById(CarId id);
    List<Car> findAll();
    List<Car> find(CarFilter filter);
    void deleteById(CarId id);
}
