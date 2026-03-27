package org.example.dealership.infrastructure.persistence.inmemory;

import org.example.dealership.domain.model.part.SparePart;
import org.example.dealership.domain.model.id.SparePartId;
import org.example.dealership.application.port.out.persistence.SparePartRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemorySparePartRepository extends InMemoryRepository<SparePartId, SparePart> implements SparePartRepository {
    @Override
    public SparePartId nextId() {
        return new SparePartId(UUID.randomUUID());
    }

    @Override
    protected SparePartId getId(SparePart entity) {
        return entity.getId();
    }

    @Override
    public void save(SparePart part) {
        super.save(part);
    }

    @Override
    public Optional<SparePart> findById(SparePartId id) {
        return super.findById(id);
    }

    @Override
    public List<SparePart> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(SparePartId id) {
        super.deleteById(id);
    }
}
