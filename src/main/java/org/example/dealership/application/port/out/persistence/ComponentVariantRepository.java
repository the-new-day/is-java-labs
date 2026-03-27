package org.example.dealership.application.port.out.persistence;

import org.example.dealership.domain.model.configuration.ComponentVariant;
import org.example.dealership.domain.model.enums.ComponentType;
import org.example.dealership.domain.model.id.CarModelId;
import org.example.dealership.domain.model.id.ComponentVariantId;

import java.util.List;
import java.util.Optional;

public interface ComponentVariantRepository {
    ComponentVariantId nextId();
    void save(ComponentVariant variant);
    Optional<ComponentVariant> findById(ComponentVariantId id);
    List<ComponentVariant> findByModelId(CarModelId modelId);
    List<ComponentVariant> findByTypeAndModel(ComponentType type, CarModelId modelId);
    List<ComponentVariant> findAll();
    void deleteById(ComponentVariantId id);
}
