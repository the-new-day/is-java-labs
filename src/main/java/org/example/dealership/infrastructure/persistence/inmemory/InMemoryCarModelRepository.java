package org.example.dealership.infrastructure.persistence.inmemory;

import org.example.dealership.domain.model.car.CarModel;
import org.example.dealership.domain.model.id.BrandId;
import org.example.dealership.domain.model.id.CarModelId;
import org.example.dealership.application.port.out.persistence.CarModelRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryCarModelRepository extends InMemoryRepository<CarModelId, CarModel> implements CarModelRepository {
    @Override
    public CarModelId nextId() {
        return new CarModelId(UUID.randomUUID());
    }

    @Override
    protected CarModelId getId(CarModel entity) {
        return entity.getId();
    }

    @Override
    public void save(CarModel model) {
        super.save(model);
    }

    @Override
    public Optional<CarModel> findById(CarModelId id) {
        return super.findById(id);
    }

    @Override
    public List<CarModel> findByBrandId(BrandId brandId) {
        return storage.values().stream()
                .filter(model -> model.getBrand().getId().equals(brandId))
                .toList();
    }

    @Override
    public List<CarModel> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(CarModelId id) {
        super.deleteById(id);
    }
}
