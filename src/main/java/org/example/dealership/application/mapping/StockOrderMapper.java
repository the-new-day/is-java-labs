package org.example.dealership.application.mapping;

import org.example.dealership.application.port.in.stockorder.dto.StockOrderDto;
import org.example.dealership.domain.model.id.CarId;
import org.example.dealership.domain.model.id.OrderId;
import org.example.dealership.domain.model.id.UserId;
import org.example.dealership.domain.model.order.StockCarOrder;

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
