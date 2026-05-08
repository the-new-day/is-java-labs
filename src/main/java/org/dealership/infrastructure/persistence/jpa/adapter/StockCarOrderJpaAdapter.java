package org.dealership.infrastructure.persistence.jpa.adapter;

import org.dealership.application.port.out.persistence.StockCarOrderRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.OrderId;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.order.StockCarOrder;
import org.dealership.infrastructure.persistence.jpa.entity.StockCarOrderJpaEntity;
import org.dealership.infrastructure.persistence.jpa.mapper.StockCarOrderJpaMapper;
import org.dealership.infrastructure.persistence.jpa.repository.StockCarOrderJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public class StockCarOrderJpaAdapter implements StockCarOrderRepository {

    private final StockCarOrderJpaRepository stockCarOrderJpaRepository;
    private final StockCarOrderJpaMapper stockCarOrderJpaMapper;

    public StockCarOrderJpaAdapter(
            StockCarOrderJpaRepository stockCarOrderJpaRepository,
            StockCarOrderJpaMapper stockCarOrderJpaMapper
    ) {
        this.stockCarOrderJpaRepository = stockCarOrderJpaRepository;
        this.stockCarOrderJpaMapper = stockCarOrderJpaMapper;
    }

    @Override
    public OrderId nextId() {
        return new OrderId(UUID.randomUUID());
    }

    @Override
    @Transactional
    public void save(StockCarOrder order) {
        Optional<StockCarOrderJpaEntity> existing =
                stockCarOrderJpaRepository.findByIdAndRemovedFalse(order.getId().value());
        if (existing.isPresent()) {
            StockCarOrderJpaEntity entity = existing.get();
            entity.setClientId(order.getClientId().value());
            entity.setManagerId(order.getManagerId().value());
            entity.setCarId(order.getCarId().value());
            entity.setStatus(order.getStatus());
            stockCarOrderJpaRepository.save(entity);
        } else {
            stockCarOrderJpaRepository.save(stockCarOrderJpaMapper.toEntity(order));
        }
    }

    @Override
    public Optional<StockCarOrder> findById(OrderId id) {
        return stockCarOrderJpaRepository.findByIdAndRemovedFalse(id.value())
                .map(stockCarOrderJpaMapper::toDomain);
    }

    @Override
    public List<StockCarOrder> findByClientId(UserId clientId) {
        return stockCarOrderJpaRepository.findAllByClientIdAndRemovedFalse(clientId.value()).stream()
                .map(stockCarOrderJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<StockCarOrder> findByManagerId(UserId managerId) {
        return stockCarOrderJpaRepository.findAllByManagerIdAndRemovedFalse(managerId.value()).stream()
                .map(stockCarOrderJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<StockCarOrder> findAll() {
        return stockCarOrderJpaRepository.findAllByRemovedFalse().stream()
                .map(stockCarOrderJpaMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(OrderId id) {
        StockCarOrderJpaEntity entity = stockCarOrderJpaRepository.findByIdAndRemovedFalse(id.value())
                .orElseThrow(() -> new EntityNotFoundException("StockCarOrder not found: " + id));
        entity.markRemoved();
        stockCarOrderJpaRepository.save(entity);
    }
}
