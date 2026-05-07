package org.dealership.application.mapper;

import org.dealership.application.port.in.common.dto.MoneyDto;
import org.dealership.domain.model.vo.Money;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MoneyMapper {

    @Mapping(target = "amount", source = "asBigDecimal")
    MoneyDto toDto(Money money);

    Money toDomain(MoneyDto dto);
}
