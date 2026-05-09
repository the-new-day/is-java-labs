package org.dealership.application.mapper;

import org.dealership.application.port.in.stockorder.dto.StockOrderStatusDto;
import org.dealership.domain.exception.DomainValidationException;
import org.dealership.domain.model.order.state.StockCarOrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockOrderStatusMapper {

    @Mapping(target = "name", source = "status")
    StockOrderStatusDto toDto(StockCarOrderStatus status);

    default StockCarOrderStatus toDomain(StockOrderStatusDto dto) {
        String name = dto.name() == null ? null : dto.name().toUpperCase();
        if ("CONFIRMED".equals(name)) {
            return StockCarOrderStatus.APPROVED_BY_MANAGER;
        }
        try {
            return StockCarOrderStatus.valueOf(name);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DomainValidationException("Unsupported stock order status: " + dto.name());
        }
    }
}
