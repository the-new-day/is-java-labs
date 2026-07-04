package org.dealership.infrastructure.persistence.jpa.adapter;

import org.dealership.application.port.out.persistence.ComponentVariantRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.configuration.ComponentVariant;
import org.dealership.domain.model.enums.ComponentType;
import org.dealership.domain.model.id.CarModelId;
import org.dealership.domain.model.id.ComponentVariantId;
import org.dealership.infrastructure.persistence.jpa.entity.CarModelJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.ComponentVariantJpaEntity;
import org.dealership.infrastructure.persistence.jpa.mapper.ComponentVariantJpaMapper;
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
public class ComponentVariantJpaAdapter implements ComponentVariantRepository {

    private final ComponentVariantJpaRepository componentVariantJpaRepository;
    private final ComponentVariantJpaMapper componentVariantJpaMapper;
    private final CarModelJpaRepository carModelJpaRepository;

    public ComponentVariantJpaAdapter(
            ComponentVariantJpaRepository componentVariantJpaRepository,
            ComponentVariantJpaMapper componentVariantJpaMapper,
            CarModelJpaRepository carModelJpaRepository
    ) {
        this.componentVariantJpaRepository = componentVariantJpaRepository;
        this.componentVariantJpaMapper = componentVariantJpaMapper;
        this.carModelJpaRepository = carModelJpaRepository;
    }

    @Override
    public ComponentVariantId nextId() {
        return new ComponentVariantId(UUID.randomUUID());
    }

    @Override
    @Transactional
    public void save(ComponentVariant variant) {
        Map<UUID, CarModelJpaEntity> compatibleModelsById = resolveCompatibleModels(variant);

        Optional<ComponentVariantJpaEntity> existing =
                componentVariantJpaRepository.findByIdAndRemovedFalse(variant.getId().value());
        if (existing.isPresent()) {
            ComponentVariantJpaEntity entity = existing.get();
            componentVariantJpaMapper.updateEntity(entity, variant, compatibleModelsById);
            componentVariantJpaRepository.save(entity);
        } else {
            componentVariantJpaRepository.save(componentVariantJpaMapper.toEntity(variant, compatibleModelsById));
        }
    }

    @Override
    public Optional<ComponentVariant> findById(ComponentVariantId id) {
        return componentVariantJpaRepository.findByIdAndRemovedFalse(id.value())
                .map(componentVariantJpaMapper::toDomain);
    }

    @Override
    public List<ComponentVariant> findByModelId(CarModelId modelId) {
        return componentVariantJpaRepository.findAllCompatibleWithModel(modelId.value()).stream()
                .map(componentVariantJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<ComponentVariant> findByTypeAndModel(ComponentType type, CarModelId modelId) {
        return componentVariantJpaRepository
                .findAllByComponentTypeAndCompatibleModel(type, modelId.value()).stream()
                .map(componentVariantJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<ComponentVariant> findAll() {
        return componentVariantJpaRepository.findAllByRemovedFalse().stream()
                .map(componentVariantJpaMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(ComponentVariantId id) {
        ComponentVariantJpaEntity entity = componentVariantJpaRepository.findByIdAndRemovedFalse(id.value())
                .orElseThrow(() -> new EntityNotFoundException("ComponentVariant not found: " + id));
        entity.markRemoved();
        componentVariantJpaRepository.save(entity);
    }

    private Map<UUID, CarModelJpaEntity> resolveCompatibleModels(ComponentVariant variant) {
        Set<UUID> modelIds = variant.getCompatibleModelIds().stream()
                .map(CarModelId::value)
                .collect(Collectors.toSet());
        return carModelJpaRepository.findAllByIdInAndRemovedFalse(modelIds).stream()
                .collect(Collectors.toMap(CarModelJpaEntity::getId, m -> m));
    }
}
