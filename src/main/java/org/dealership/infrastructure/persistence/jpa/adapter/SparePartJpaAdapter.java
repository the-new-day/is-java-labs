package org.dealership.infrastructure.persistence.jpa.adapter;

import org.dealership.application.port.out.persistence.SparePartRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.CarModelId;
import org.dealership.domain.model.id.SparePartId;
import org.dealership.domain.model.part.SparePart;
import org.dealership.infrastructure.persistence.jpa.entity.CarModelJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.SparePartJpaEntity;
import org.dealership.infrastructure.persistence.jpa.mapper.SparePartJpaMapper;
import org.dealership.infrastructure.persistence.jpa.repository.CarModelJpaRepository;
import org.dealership.infrastructure.persistence.jpa.repository.SparePartJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class SparePartJpaAdapter implements SparePartRepository {

    private final SparePartJpaRepository sparePartJpaRepository;
    private final SparePartJpaMapper sparePartJpaMapper;
    private final CarModelJpaRepository carModelJpaRepository;

    public SparePartJpaAdapter(
            SparePartJpaRepository sparePartJpaRepository,
            SparePartJpaMapper sparePartJpaMapper,
            CarModelJpaRepository carModelJpaRepository
    ) {
        this.sparePartJpaRepository = sparePartJpaRepository;
        this.sparePartJpaMapper = sparePartJpaMapper;
        this.carModelJpaRepository = carModelJpaRepository;
    }

    @Override
    public SparePartId nextId() {
        return new SparePartId(UUID.randomUUID());
    }

    @Override
    @Transactional
    public void save(SparePart part) {
        Map<UUID, CarModelJpaEntity> compatibleModelsById = resolveCompatibleModels(part);

        Optional<SparePartJpaEntity> existing =
                sparePartJpaRepository.findByIdAndRemovedFalse(part.getId().value());
        if (existing.isPresent()) {
            SparePartJpaEntity entity = existing.get();
            sparePartJpaMapper.updateEntity(entity, part, compatibleModelsById);
            sparePartJpaRepository.save(entity);
        } else {
            sparePartJpaRepository.save(sparePartJpaMapper.toEntity(part, compatibleModelsById));
        }
    }

    @Override
    public Optional<SparePart> findById(SparePartId id) {
        return sparePartJpaRepository.findByIdAndRemovedFalse(id.value())
                .map(sparePartJpaMapper::toDomain);
    }

    @Override
    public List<SparePart> findAll() {
        return sparePartJpaRepository.findAllByRemovedFalse().stream()
                .map(sparePartJpaMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(SparePartId id) {
        SparePartJpaEntity entity = sparePartJpaRepository.findByIdAndRemovedFalse(id.value())
                .orElseThrow(() -> new EntityNotFoundException("SparePart not found: " + id));
        entity.markRemoved();
        sparePartJpaRepository.save(entity);
    }

    private Map<UUID, CarModelJpaEntity> resolveCompatibleModels(SparePart part) {
        Set<UUID> modelIds = part.getCompatibleModelIds().stream()
                .map(CarModelId::value)
                .collect(Collectors.toSet());
        return carModelJpaRepository.findAllByIdInAndRemovedFalse(modelIds).stream()
                .collect(Collectors.toMap(CarModelJpaEntity::getId, m -> m));
    }
}
