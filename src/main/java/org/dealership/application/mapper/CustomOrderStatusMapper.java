package org.dealership.application.mapper;

import org.dealership.application.port.in.customorder.dto.CustomOrderStatusDto;
import org.dealership.domain.exception.DomainValidationException;
import org.dealership.domain.model.order.state.CustomCarOrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomOrderStatusMapper {

    @Mapping(target = "name", source = "status")
    CustomOrderStatusDto toDto(CustomCarOrderStatus status);

    default CustomCarOrderStatus toDomain(CustomOrderStatusDto dto) {
        try {
            return CustomCarOrderStatus.valueOf(dto.name().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DomainValidationException("Unsupported custom order status: " + dto.name());
        }
    }
}
