package org.dealership.infrastructure.persistence.jpa.adapter;

import org.dealership.application.port.out.persistence.BrandRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.car.Brand;
import org.dealership.domain.model.id.BrandId;
import org.dealership.infrastructure.persistence.jpa.entity.BrandJpaEntity;
import org.dealership.infrastructure.persistence.jpa.mapper.BrandJpaMapper;
import org.dealership.infrastructure.persistence.jpa.repository.BrandJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BrandJpaAdapter implements BrandRepository {

    private final BrandJpaRepository brandJpaRepository;
    private final BrandJpaMapper brandJpaMapper;

    public BrandJpaAdapter(BrandJpaRepository brandJpaRepository, BrandJpaMapper brandJpaMapper) {
        this.brandJpaRepository = brandJpaRepository;
        this.brandJpaMapper = brandJpaMapper;
    }

    @Override
    public BrandId nextId() {
        return new BrandId(UUID.randomUUID());
    }

    @Override
    @Transactional
    public void save(Brand brand) {
        Optional<BrandJpaEntity> existing = brandJpaRepository.findByIdAndRemovedFalse(brand.getId().value());
        if (existing.isPresent()) {
            BrandJpaEntity entity = existing.get();
            entity.setName(brand.getName());
            brandJpaRepository.save(entity);
        } else {
            brandJpaRepository.save(brandJpaMapper.toEntity(brand));
        }
    }

    @Override
    public Optional<Brand> findById(BrandId id) {
        return brandJpaRepository.findByIdAndRemovedFalse(id.value())
                .map(brandJpaMapper::toDomain);
    }

    @Override
    public List<Brand> findAll() {
        return brandJpaRepository.findAllByRemovedFalse().stream()
                .map(brandJpaMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(BrandId id) {
        BrandJpaEntity entity = brandJpaRepository.findByIdAndRemovedFalse(id.value())
                .orElseThrow(() -> new EntityNotFoundException("Brand not found: " + id));
        entity.markRemoved();
        brandJpaRepository.save(entity);
    }
}
