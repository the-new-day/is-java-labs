package org.example.dealership.infrastructure.persistence.inmemory;

import org.example.dealership.domain.model.car.Brand;
import org.example.dealership.domain.model.id.BrandId;
import org.example.dealership.application.port.out.persistence.BrandRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryBrandRepository extends InMemoryRepository<BrandId, Brand> implements BrandRepository {
    @Override
    public BrandId nextId() {
        return new BrandId(UUID.randomUUID());
    }

    @Override
    protected BrandId getId(Brand entity) {
        return entity.getId();
    }

    @Override
    public void save(Brand brand) {
        super.save(brand);
    }

    @Override
    public Optional<Brand> findById(BrandId id) {
        return super.findById(id);
    }

    @Override
    public List<Brand> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(BrandId id) {
        super.deleteById(id);
    }
}
