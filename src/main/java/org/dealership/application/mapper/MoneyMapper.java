package org.dealership.application.mapper;

import org.dealership.application.port.in.common.dto.MoneyDto;
import org.dealership.domain.model.vo.Money;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MoneyMapper {

    default MoneyDto toDto(Money money) {
        return new MoneyDto(money.asBigDecimal());
    }

    default Money toDomain(MoneyDto dto) {
        return new Money(dto.amount());
    }
}
