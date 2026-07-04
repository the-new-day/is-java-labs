package org.dealership.infrastructure.persistence.jpa.adapter;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.dealership.application.port.out.persistence.CarRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.configuration.Configuration;
import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.carfilter.CarFilter;
import org.dealership.domain.model.vo.Money;
import org.dealership.infrastructure.persistence.jpa.entity.CarJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.CarModelJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.ComponentVariantJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.ConfigurationJpaEntity;
import org.dealership.infrastructure.persistence.jpa.mapper.CarJpaMapper;
import org.dealership.infrastructure.persistence.jpa.mapper.ConfigurationJpaMapper;
import org.dealership.infrastructure.persistence.jpa.repository.CarJpaRepository;
import org.dealership.infrastructure.persistence.jpa.repository.CarModelJpaRepository;
import org.dealership.infrastructure.persistence.jpa.repository.ComponentVariantJpaRepository;
import org.dealership.infrastructure.persistence.jpa.repository.ConfigurationJpaRepository;
import org.dealership.infrastructure.persistence.jpa.specification.CarSpecificationConverter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@Transactional(readOnly = true)
public class CarJpaAdapter implements CarRepository {

    private final CarJpaRepository carJpaRepository;
    private final CarJpaMapper carJpaMapper;
    private final ConfigurationJpaMapper configurationJpaMapper;
    private final ConfigurationJpaRepository configurationJpaRepository;
    private final CarModelJpaRepository carModelJpaRepository;
    private final ComponentVariantJpaRepository componentVariantJpaRepository;
    private final CarSpecificationConverter carSpecificationConverter;

    public CarJpaAdapter(
            CarJpaRepository carJpaRepository,
            CarJpaMapper carJpaMapper,
            ConfigurationJpaMapper configurationJpaMapper,
            ConfigurationJpaRepository configurationJpaRepository,
            CarModelJpaRepository carModelJpaRepository,
            ComponentVariantJpaRepository componentVariantJpaRepository,
            CarSpecificationConverter carSpecificationConverter
    ) {
        this.carJpaRepository = carJpaRepository;
        this.carJpaMapper = carJpaMapper;
        this.configurationJpaMapper = configurationJpaMapper;
        this.configurationJpaRepository = configurationJpaRepository;
        this.carModelJpaRepository = carModelJpaRepository;
        this.componentVariantJpaRepository = componentVariantJpaRepository;
        this.carSpecificationConverter = carSpecificationConverter;
    }

    @Override
    public CarId nextId() {
        return new CarId(UUID.randomUUID());
    }

    @Override
    @Transactional
    public void save(Car car) {
        Configuration config = car.getConfiguration();

        CarModelJpaEntity carModelEntity = carModelJpaRepository
                .findByIdAndRemovedFalse(config.getModel().getId().value())
                .orElseThrow(() -> new EntityNotFoundException("CarModel not found: " + config.getModel().getId()));

        Map<UUID, ComponentVariantJpaEntity> variantEntitiesById = resolveVariants(config);

        Optional<CarJpaEntity> existing = carJpaRepository.findByIdAndRemovedFalse(car.getId().value());
        if (existing.isPresent()) {
            CarJpaEntity entity = existing.get();
            ConfigurationJpaEntity configEntity = entity.getConfiguration();
            configurationJpaMapper.updateEntity(configEntity, config, carModelEntity, variantEntitiesById);
            configurationJpaRepository.save(configEntity);
            carJpaMapper.updateEntity(entity, car, configEntity);
            carJpaRepository.save(entity);
        } else {
            ConfigurationJpaEntity configEntity = configurationJpaRepository.save(
                    configurationJpaMapper.toEntity(config, carModelEntity, variantEntitiesById));
            carJpaRepository.save(carJpaMapper.toEntity(car, configEntity));
        }
    }

    @Override
    public Optional<Car> findById(CarId id) {
        return carJpaRepository.findByIdAndRemovedFalse(id.value())
                .map(carJpaMapper::toDomain);
    }

    @Override
    public List<Car> findAll() {
        return carJpaRepository.findAllByRemovedFalse().stream()
                .map(carJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<Car> findByFilter(CarFilter filter) {
        Specification<CarJpaEntity> spec = carSpecificationConverter.convertFromFilter(filter);
        List<Car> cars = carJpaRepository.findAll(spec).stream()
                .map(carJpaMapper::toDomain)
                .toList();
        return filterByPrice(cars, filter.minPrice(), filter.maxPrice());
    }

    @Override
    @Transactional
    public void deleteById(CarId id) {
        CarJpaEntity entity = carJpaRepository.findByIdAndRemovedFalse(id.value())
                .orElseThrow(() -> new EntityNotFoundException("Car not found: " + id));
        entity.markRemoved();
        carJpaRepository.save(entity);
    }

    private Map<UUID, ComponentVariantJpaEntity> resolveVariants(Configuration config) {
        Set<UUID> variantIds = config.getComponentVariantSelection().asMap().values().stream()
                .map(v -> v.getId().value())
                .collect(Collectors.toSet());
        return componentVariantJpaRepository.findAllByIdInAndRemovedFalse(variantIds).stream()
                .collect(Collectors.toMap(ComponentVariantJpaEntity::getId, v -> v));
    }

    private List<Car> filterByPrice(List<Car> cars, Money minPrice, Money maxPrice) {
        if (minPrice == null && maxPrice == null) {
            return cars;
        }
        return cars.stream()
                .filter(car -> {
                    Money price = car.getPrice();
                    if (minPrice != null && price.compareTo(minPrice) < 0) return false;
                    if (maxPrice != null && price.compareTo(maxPrice) > 0) return false;
                    return true;
                })
                .toList();
    }
}
