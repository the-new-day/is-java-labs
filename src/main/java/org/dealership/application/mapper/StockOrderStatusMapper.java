package org.dealership.application.mapper;

import org.dealership.application.port.in.stockorder.dto.StockOrderStatusDto;
import org.dealership.domain.model.order.state.StockCarOrderStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockOrderStatusMapper {
    StockOrderStatusDto toDto(StockCarOrderStatus status);
    StockCarOrderStatus toDomain(StockOrderStatusDto dto);
}
