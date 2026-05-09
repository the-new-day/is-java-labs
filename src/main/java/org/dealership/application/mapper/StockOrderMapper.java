package org.dealership.application.mapper;

import org.dealership.application.port.in.stockorder.dto.StockOrderDto;
import org.dealership.domain.model.order.StockCarOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BaseIdMapper.class, StockOrderStatusMapper.class})
public interface StockOrderMapper {

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "clientId", source = "clientId.value")
    @Mapping(target = "managerId", source = "managerId.value")
    @Mapping(target = "carId", source = "carId.value")
    StockOrderDto toDto(StockCarOrder order);

    @Mapping(target = "id", source = "id", qualifiedByName = "toOrderId")
    @Mapping(target = "clientId", source = "clientId", qualifiedByName = "toUserId")
    @Mapping(target = "managerId", source = "managerId", qualifiedByName = "toUserId")
    @Mapping(target = "carId", source = "carId", qualifiedByName = "toCarId")
    @Mapping(target = "withStatus", ignore = true)
    StockCarOrder toDomain(StockOrderDto dto);
}
