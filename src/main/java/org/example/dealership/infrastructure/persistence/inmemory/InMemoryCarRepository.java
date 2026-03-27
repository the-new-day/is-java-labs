package org.example.dealership.infrastructure.persistence.inmemory;

import org.example.dealership.domain.model.car.Car;
import org.example.dealership.domain.model.carfilter.CarFilter;
import org.example.dealership.domain.model.id.CarId;
import org.example.dealership.application.port.out.persistence.CarRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryCarRepository extends InMemoryRepository<CarId, Car> implements CarRepository {
    @Override
    public CarId nextId() {
        return new CarId(UUID.randomUUID());
    }

    @Override
    protected CarId getId(Car entity) {
        return entity.getId();
    }

    @Override
    public void save(Car car) {
        super.save(car);
    }

    @Override
    public Optional<Car> findById(CarId id) {
        return super.findById(id);
    }

    @Override
    public List<Car> findAll() {
        return super.findAll();
    }

    @Override
    public List<Car> find(CarFilter filter) {
        return findAll().stream()
                .filter(filter::matches)
                .toList();
    }

    @Override
    public void deleteById(CarId id) {
        super.deleteById(id);
    }
}
