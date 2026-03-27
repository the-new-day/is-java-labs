package org.example.dealership.application.port.out.persistence;

import org.example.dealership.domain.model.car.Brand;
import org.example.dealership.domain.model.id.BrandId;

import java.util.List;
import java.util.Optional;

public interface BrandRepository {
    BrandId nextId();
    void save(Brand brand);
    Optional<Brand> findById(BrandId id);
    List<Brand> findAll();
    void deleteById(BrandId id);
}
