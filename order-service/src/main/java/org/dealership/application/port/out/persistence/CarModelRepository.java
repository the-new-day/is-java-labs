package org.dealership.application.port.out.persistence;

import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.id.BrandId;
import org.dealership.domain.model.id.CarModelId;

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
