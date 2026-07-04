package org.dealership.infrastructure.persistence.jpa.adapter;

import org.dealership.application.port.out.persistence.AssemblyOrderRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.assembly.AssemblyOrder;
import org.dealership.domain.model.id.AssemblyOrderId;
import org.dealership.infrastructure.persistence.jpa.entity.AssemblyOrderJpaEntity;
import org.dealership.infrastructure.persistence.jpa.mapper.AssemblyOrderJpaMapper;
import org.dealership.infrastructure.persistence.jpa.repository.AssemblyOrderJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public class AssemblyOrderJpaAdapter implements AssemblyOrderRepository {

    private final AssemblyOrderJpaRepository jpaRepository;
    private final AssemblyOrderJpaMapper mapper;

    public AssemblyOrderJpaAdapter(AssemblyOrderJpaRepository jpaRepository, AssemblyOrderJpaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public AssemblyOrderId nextId() {
        return new AssemblyOrderId(UUID.randomUUID());
    }

    @Override
    @Transactional
    public void save(AssemblyOrder order) {
        Optional<AssemblyOrderJpaEntity> existing =
                jpaRepository.findByIdAndRemovedFalse(order.getId().value());
        if (existing.isPresent()) {
            AssemblyOrderJpaEntity entity = existing.get();
            mapper.updateEntity(entity, order);
            jpaRepository.save(entity);
        } else {
            jpaRepository.save(mapper.toEntity(order));
        }
    }

    @Override
    public Optional<AssemblyOrder> findById(AssemblyOrderId id) {
        return jpaRepository.findByIdAndRemovedFalse(id.value())
                .map(mapper::toDomain);
    }

    @Override
    public List<AssemblyOrder> findAll() {
        return jpaRepository.findAllByRemovedFalse().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(AssemblyOrderId id) {
        AssemblyOrderJpaEntity entity = jpaRepository.findByIdAndRemovedFalse(id.value())
                .orElseThrow(() -> new EntityNotFoundException("Assembly order not found: " + id));
        entity.markRemoved();
        jpaRepository.save(entity);
    }
}
