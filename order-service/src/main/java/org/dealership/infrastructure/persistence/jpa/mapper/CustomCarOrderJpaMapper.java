package org.dealership.infrastructure.persistence.jpa.mapper;

import org.dealership.domain.model.configuration.Configuration;
import org.dealership.domain.model.id.OrderId;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.order.CustomCarOrder;
import org.dealership.infrastructure.persistence.jpa.entity.ConfigurationJpaEntity;
import org.dealership.infrastructure.persistence.jpa.entity.CustomCarOrderJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomCarOrderJpaMapper {

    private final ConfigurationJpaMapper configurationMapper;

    public CustomCarOrderJpaMapper(ConfigurationJpaMapper configurationMapper) {
        this.configurationMapper = configurationMapper;
    }

    public CustomCarOrder toDomain(CustomCarOrderJpaEntity entity) {
        Configuration configuration = configurationMapper.toDomain(entity.getConfiguration());
        return new CustomCarOrder(
                new OrderId(entity.getId()),
                new UserId(entity.getClientId()),
                new UserId(entity.getManagerId()),
                configuration,
                entity.getStatus()
        );
    }

    public CustomCarOrderJpaEntity toEntity(CustomCarOrder order, ConfigurationJpaEntity configurationEntity) {
        return new CustomCarOrderJpaEntity(
                order.getId().value(),
                order.getClientId().value(),
                order.getManagerId().value(),
                configurationEntity,
                order.getStatus()
        );
    }

    public void updateEntity(CustomCarOrderJpaEntity entity, CustomCarOrder order, ConfigurationJpaEntity configurationEntity) {
        entity.setClientId(order.getClientId().value());
        entity.setManagerId(order.getManagerId().value());
        entity.setConfiguration(configurationEntity);
        entity.setStatus(order.getStatus());
    }
}
