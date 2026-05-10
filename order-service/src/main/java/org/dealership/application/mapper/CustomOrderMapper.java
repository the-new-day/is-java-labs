package org.dealership.application.mapper;

import org.dealership.application.port.in.customorder.dto.CustomOrderDto;
import org.dealership.domain.model.order.CustomCarOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        BaseIdMapper.class,
        CustomOrderStatusMapper.class,
        ConfigurationMapper.class
})
public interface CustomOrderMapper {

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "clientId", source = "clientId.value")
    @Mapping(target = "managerId", source = "managerId.value")
    CustomOrderDto toDto(CustomCarOrder order);

    @Mapping(target = "id", source = "id", qualifiedByName = "toOrderId")
    @Mapping(target = "clientId", source = "clientId", qualifiedByName = "toUserId")
    @Mapping(target = "managerId", source = "managerId", qualifiedByName = "toUserId")
    CustomCarOrder toDomain(CustomOrderDto order);
}
