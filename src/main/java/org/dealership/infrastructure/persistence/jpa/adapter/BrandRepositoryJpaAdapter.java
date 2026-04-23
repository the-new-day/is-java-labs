package org.dealership.infrastructure.persistence.jpa.adapter;

import org.dealership.application.port.out.persistence.BrandRepository;
import org.dealership.domain.model.car.Brand;
import org.dealership.domain.model.id.BrandId;
import org.dealership.infrastructure.persistence.jpa.mapper.BrandJpaMapper;
import org.dealership.infrastructure.persistence.jpa.repository.BrandJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class BrandRepositoryJpaAdapter implements BrandRepository {
    private final BrandJpaRepository brandJpaRepository;
    private final BrandJpaMapper brandJpaMapper;

    public BrandRepositoryJpaAdapter(
            BrandJpaRepository brandJpaRepository,
            BrandJpaMapper brandJpaMapper
    ) {
        this.brandJpaRepository = brandJpaRepository;
        this.brandJpaMapper = brandJpaMapper;
    }

    @Override
    public BrandId nextId() {
        return new BrandId(UUID.randomUUID());
    }

    @Override
    public void save(Brand brand) {
        brandJpaRepository.save(brandJpaMapper.toEntity(brand));
    }

    @Override
    public Optional<Brand> findById(BrandId id) {
        return brandJpaRepository.findByIdAndRemovedFalse(id.value())
                .map(brandJpaMapper::toDomain);
    }

    @Override
    public List<Brand> findAll() {
        return brandJpaRepository.findAllByRemovedFalse()
                .stream()
                .map(brandJpaMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(BrandId id) {
        brandJpaRepository.findByIdAndRemovedFalse(id.value())
                .ifPresent(entity -> {
                    entity.markRemoved();
                    brandJpaRepository.save(entity);
                });
    }
}
