package org.example.dealership.application.port.out.persistence;

import org.example.dealership.domain.model.car.Car;
import org.example.dealership.domain.model.id.CarId;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    CarId nextId();
    void save(Car car);
    Optional<Car> findById(CarId id);
    List<Car> findAll();
    void deleteById(CarId id);
}
