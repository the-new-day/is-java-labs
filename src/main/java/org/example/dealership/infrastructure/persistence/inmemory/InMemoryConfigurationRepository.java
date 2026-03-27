package org.example.dealership.infrastructure.persistence.inmemory;

import org.example.dealership.domain.model.configuration.Configuration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryConfigurationRepository
        extends InMemoryRepository<ConfigurationId, Configuration>
        implements ConfigurationRepository {
    @Override
    public ConfigurationId nextId() {
        return new ConfigurationId(UUID.randomUUID());
    }

    @Override
    protected ConfigurationId getId(Configuration entity) {
        return entity.getId();
    }

    @Override
    public void save(Configuration configuration) {
        super.save(configuration);
    }

    @Override
    public Optional<Configuration> findById(ConfigurationId id) {
        return super.findById(id);
    }

    @Override
    public List<Configuration> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(ConfigurationId id) {
        super.deleteById(id);
    }
}
