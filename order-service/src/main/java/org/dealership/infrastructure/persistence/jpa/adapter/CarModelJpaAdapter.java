package org.dealership.infrastructure.persistence.jpa.adapter;

import org.dealership.application.port.out.persistence.CarModelRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.id.BrandId;
import org.dealership.domain.model.id.CarModelId;
import org.dealership.infrastructure.persistence.jpa.entity.BrandJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.CarModelJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.ComponentVariantJpaEntity;
import org.dealership.infrastructure.persistence.jpa.mapper.CarModelJpaMapper;
import org.dealership.infrastructure.persistence.jpa.repository.BrandJpaRepository;
import org.dealership.infrastructure.persistence.jpa.repository.CarModelJpaRepository;
import org.dealership.infrastructure.persistence.jpa.repository.ComponentVariantJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class CarModelJpaAdapter implements CarModelRepository {

    private final CarModelJpaRepository carModelJpaRepository;
    private final CarModelJpaMapper carModelJpaMapper;
    private final BrandJpaRepository brandJpaRepository;
    private final ComponentVariantJpaRepository componentVariantJpaRepository;

    public CarModelJpaAdapter(
            CarModelJpaRepository carModelJpaRepository,
            CarModelJpaMapper carModelJpaMapper,
            BrandJpaRepository brandJpaRepository,
            ComponentVariantJpaRepository componentVariantJpaRepository
    ) {
        this.carModelJpaRepository = carModelJpaRepository;
        this.carModelJpaMapper = carModelJpaMapper;
        this.brandJpaRepository = brandJpaRepository;
        this.componentVariantJpaRepository = componentVariantJpaRepository;
    }

    @Override
    public CarModelId nextId() {
        return new CarModelId(UUID.randomUUID());
    }

    @Override
    @Transactional
    public void save(CarModel model) {
        BrandJpaEntity brandEntity = brandJpaRepository
                .findByIdAndRemovedFalse(model.getBrand().getId().value())
                .orElseThrow(() -> new EntityNotFoundException("Brand not found: " + model.getBrand().getId()));

        Map<UUID, ComponentVariantJpaEntity> variantEntitiesById = resolveVariants(model);

        Optional<CarModelJpaEntity> existing =
                carModelJpaRepository.findByIdAndRemovedFalse(model.getId().value());
        if (existing.isPresent()) {
            CarModelJpaEntity entity = existing.get();
            carModelJpaMapper.updateEntity(entity, model, brandEntity, variantEntitiesById);
            carModelJpaRepository.save(entity);
        } else {
            carModelJpaRepository.save(carModelJpaMapper.toEntity(model, brandEntity, variantEntitiesById));
        }
    }

    @Override
    public Optional<CarModel> findById(CarModelId id) {
        return carModelJpaRepository.findByIdAndRemovedFalse(id.value())
                .map(carModelJpaMapper::toDomain);
    }

    @Override
    public List<CarModel> findByBrandId(BrandId brandId) {
        return carModelJpaRepository.findAllByBrandIdAndRemovedFalse(brandId.value()).stream()
                .map(carModelJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<CarModel> findAll() {
        return carModelJpaRepository.findAllByRemovedFalse().stream()
                .map(carModelJpaMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(CarModelId id) {
        CarModelJpaEntity entity = carModelJpaRepository.findByIdAndRemovedFalse(id.value())
                .orElseThrow(() -> new EntityNotFoundException("CarModel not found: " + id));
        entity.markRemoved();
        carModelJpaRepository.save(entity);
    }

    private Map<UUID, ComponentVariantJpaEntity> resolveVariants(CarModel model) {
        Set<UUID> variantIds = model.getBaseComponentSelection().asMap().values().stream()
                .map(v -> v.getId().value())
                .collect(Collectors.toSet());
        return componentVariantJpaRepository.findAllByIdInAndRemovedFalse(variantIds).stream()
                .collect(Collectors.toMap(ComponentVariantJpaEntity::getId, v -> v));
    }
}
