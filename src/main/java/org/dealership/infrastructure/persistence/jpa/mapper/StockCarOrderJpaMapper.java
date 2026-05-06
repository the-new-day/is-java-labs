package org.dealership.infrastructure.persistence.jpa.mapper;

import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.id.OrderId;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.order.StockCarOrder;
import org.dealership.infrastructure.persistence.jpa.entity.StockCarOrderJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class StockCarOrderJpaMapper {
    public StockCarOrder toDomain(StockCarOrderJpaEntity entity) {
        return new StockCarOrder(
                new OrderId(entity.getId()),
                new UserId(entity.getClientId()),
                new UserId(entity.getManagerId()),
                new CarId(entity.getCarId()),
                entity.getStatus()
        );
    }

    public StockCarOrderJpaEntity toEntity(StockCarOrder order) {
        return new StockCarOrderJpaEntity(
                order.getId().value(),
                order.getClientId().value(),
                order.getManagerId().value(),
                order.getCarId().value(),
                order.getStatus()
        );
    }
}
