package org.dealership.infrastructure.persistence.jpa.adapter;

import org.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.configuration.Configuration;
import org.dealership.domain.model.id.OrderId;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.order.CustomCarOrder;
import org.dealership.infrastructure.persistence.jpa.entity.CarModelJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.ComponentVariantJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.ConfigurationJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.CustomCarOrderJpaEntity;
import org.dealership.infrastructure.persistence.jpa.mapper.ConfigurationJpaMapper;
import org.dealership.infrastructure.persistence.jpa.mapper.CustomCarOrderJpaMapper;
import org.dealership.infrastructure.persistence.jpa.repository.CarModelJpaRepository;
import org.dealership.infrastructure.persistence.jpa.repository.ComponentVariantJpaRepository;
import org.dealership.infrastructure.persistence.jpa.repository.ConfigurationJpaRepository;
import org.dealership.infrastructure.persistence.jpa.repository.CustomCarOrderJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class CustomCarOrderJpaAdapter implements CustomCarOrderRepository {

    private final CustomCarOrderJpaRepository customCarOrderJpaRepository;
    private final CustomCarOrderJpaMapper customCarOrderJpaMapper;
    private final ConfigurationJpaMapper configurationJpaMapper;
    private final ConfigurationJpaRepository configurationJpaRepository;
    private final CarModelJpaRepository carModelJpaRepository;
    private final ComponentVariantJpaRepository componentVariantJpaRepository;

    public CustomCarOrderJpaAdapter(
            CustomCarOrderJpaRepository customCarOrderJpaRepository,
            CustomCarOrderJpaMapper customCarOrderJpaMapper,
            ConfigurationJpaMapper configurationJpaMapper,
            ConfigurationJpaRepository configurationJpaRepository,
            CarModelJpaRepository carModelJpaRepository,
            ComponentVariantJpaRepository componentVariantJpaRepository
    ) {
        this.customCarOrderJpaRepository = customCarOrderJpaRepository;
        this.customCarOrderJpaMapper = customCarOrderJpaMapper;
        this.configurationJpaMapper = configurationJpaMapper;
        this.configurationJpaRepository = configurationJpaRepository;
        this.carModelJpaRepository = carModelJpaRepository;
        this.componentVariantJpaRepository = componentVariantJpaRepository;
    }

    @Override
    public OrderId nextId() {
        return new OrderId(UUID.randomUUID());
    }

    @Override
    @Transactional
    public void save(CustomCarOrder order) {
        Configuration config = order.getConfiguration();

        CarModelJpaEntity carModelEntity = carModelJpaRepository
                .findByIdAndRemovedFalse(config.getModel().getId().value())
                .orElseThrow(() -> new EntityNotFoundException("CarModel not found: " + config.getModel().getId()));

        Map<UUID, ComponentVariantJpaEntity> variantEntitiesById = resolveVariants(config);

        Optional<CustomCarOrderJpaEntity> existing =
                customCarOrderJpaRepository.findByIdAndRemovedFalse(order.getId().value());
        if (existing.isPresent()) {
            CustomCarOrderJpaEntity entity = existing.get();
            ConfigurationJpaEntity configEntity = entity.getConfiguration();
            configurationJpaMapper.updateEntity(configEntity, config, carModelEntity, variantEntitiesById);
            configurationJpaRepository.save(configEntity);
            customCarOrderJpaMapper.updateEntity(entity, order, configEntity);
            customCarOrderJpaRepository.save(entity);
        } else {
            ConfigurationJpaEntity configEntity = configurationJpaRepository.save(
                    configurationJpaMapper.toEntity(config, carModelEntity, variantEntitiesById));
            customCarOrderJpaRepository.save(customCarOrderJpaMapper.toEntity(order, configEntity));
        }
    }

    @Override
    public Optional<CustomCarOrder> findById(OrderId id) {
        return customCarOrderJpaRepository.findByIdAndRemovedFalse(id.value())
                .map(customCarOrderJpaMapper::toDomain);
    }

    @Override
    public List<CustomCarOrder> findByClientId(UserId clientId) {
        return customCarOrderJpaRepository.findAllByClientIdAndRemovedFalse(clientId.value()).stream()
                .map(customCarOrderJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<CustomCarOrder> findByManagerId(UserId managerId) {
        return customCarOrderJpaRepository.findAllByManagerIdAndRemovedFalse(managerId.value()).stream()
                .map(customCarOrderJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<CustomCarOrder> findAll() {
        return customCarOrderJpaRepository.findAllByRemovedFalse().stream()
                .map(customCarOrderJpaMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(OrderId id) {
        CustomCarOrderJpaEntity entity = customCarOrderJpaRepository.findByIdAndRemovedFalse(id.value())
                .orElseThrow(() -> new EntityNotFoundException("CustomCarOrder not found: " + id));
        entity.markRemoved();
        customCarOrderJpaRepository.save(entity);
    }

    private Map<UUID, ComponentVariantJpaEntity> resolveVariants(Configuration config) {
        Set<UUID> variantIds = config.getComponentVariantSelection().asMap().values().stream()
                .map(v -> v.getId().value())
                .collect(Collectors.toSet());
        return componentVariantJpaRepository.findAllByIdInAndRemovedFalse(variantIds).stream()
                .collect(Collectors.toMap(ComponentVariantJpaEntity::getId, v -> v));
    }
}
