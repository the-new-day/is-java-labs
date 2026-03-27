package org.example.dealership.application.port.out.persistence;

import org.example.dealership.domain.model.part.SparePart;
import org.example.dealership.domain.model.id.SparePartId;

import java.util.List;
import java.util.Optional;

public interface SparePartRepository {
    SparePartId nextId();
    void save(SparePart part);
    Optional<SparePart> findById(SparePartId id);
    List<SparePart> findAll();
    void deleteById(SparePartId id);
}
