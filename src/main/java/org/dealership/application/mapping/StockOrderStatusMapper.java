package org.dealership.application.mapping;

import org.dealership.application.port.in.stockorder.dto.StockOrderStatusDto;
import org.dealership.domain.exception.DomainValidationException;
import org.dealership.domain.model.order.state.StockCarOrderStatus;

public class StockOrderStatusMapper {
    public static StockOrderStatusDto mapToDto(StockCarOrderStatus status) {
        return new StockOrderStatusDto(status.name());
    }

    public static StockCarOrderStatus mapFromDto(StockOrderStatusDto dto) {
        try {
            return StockCarOrderStatus.valueOf(dto.name().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DomainValidationException("Unsupported stock order status: " + dto.name());
        }
    }
}
