package org.example.dealership.application.port.out.persistence;

import org.example.dealership.domain.model.car.CarModel;
import org.example.dealership.domain.model.id.BrandId;
import org.example.dealership.domain.model.id.CarModelId;

import java.util.List;
import java.util.Optional;

public interface CarModelRepository {
    CarModelId nextId();
    void save(CarModel model);
    Optional<CarModel> findById(CarModelId id);
    List<CarModel> findByBrandId(BrandId brandId);
    List<CarModel> findAll();
    void deleteById(CarModelId id);
}
