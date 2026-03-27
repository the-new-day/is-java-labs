package org.example.dealership.infrastructure.persistence.inmemory;

import org.example.dealership.domain.model.configuration.ComponentVariant;
import org.example.dealership.domain.model.enums.ComponentType;
import org.example.dealership.domain.model.id.CarModelId;
import org.example.dealership.domain.model.id.ComponentVariantId;
import org.example.dealership.application.port.out.persistence.ComponentVariantRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryComponentVariantRepository extends InMemoryRepository<ComponentVariantId, ComponentVariant> implements ComponentVariantRepository {
    @Override
    public ComponentVariantId nextId() {
        return new ComponentVariantId(UUID.randomUUID());
    }

    @Override
    protected ComponentVariantId getId(ComponentVariant entity) {
        return entity.getId();
    }

    @Override
    public void save(ComponentVariant variant) {
        super.save(variant);
    }

    @Override
    public Optional<ComponentVariant> findById(ComponentVariantId id) {
        return super.findById(id);
    }

    @Override
    public List<ComponentVariant> findByModelId(CarModelId modelId) {
        return storage.values().stream()
                .filter(variant -> variant.isCompatibleWith(modelId))
                .toList();
    }

    @Override
    public List<ComponentVariant> findByTypeAndModel(ComponentType type, CarModelId modelId) {
        return storage.values().stream()
                .filter(variant -> variant.getComponentType() == type)
                .filter(variant -> variant.isCompatibleWith(modelId))
                .toList();
    }

    @Override
    public List<ComponentVariant> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(ComponentVariantId id) {
        super.deleteById(id);
    }
}
