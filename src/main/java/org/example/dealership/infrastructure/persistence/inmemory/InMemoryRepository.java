package org.example.dealership.infrastructure.persistence.inmemory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class InMemoryRepository<ID, T> {
    protected final map<ID, T> storage = new HashMap<>();

    protected abstract ID getId(T entity);

    public void save(T entity) {
        storage.put(getId(entity), entity);
    }

    public Optional<T> findById(ID id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<T> findAll() {
        return List.copyOf(storage.values());
    }

    public void deleteById(ID id) {
        storage.remove(id);
    }
}
