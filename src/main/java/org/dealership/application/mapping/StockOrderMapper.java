package org.dealership.application.mapping;

import org.dealership.application.port.in.stockorder.dto.StockOrderDto;
import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.id.OrderId;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.order.StockCarOrder;

public class StockOrderMapper {
    public static StockOrderDto mapToDto(StockCarOrder order) {
        return new StockOrderDto(
                order.getId().value(),
                order.getClientId().value(),
                order.getManagerId().value(),
                order.getCarId().value(),
                StockOrderStatusMapper.mapToDto(order.getStatus())
        );
    }

    public static StockCarOrder mapFromDto(StockOrderDto dto) {
        return new StockCarOrder(
                new OrderId(dto.id()),
                new UserId(dto.clientId()),
                new UserId(dto.managerId()),
                new CarId(dto.carId()),
                StockOrderStatusMapper.mapFromDto(dto.status())
        );
    }
}
