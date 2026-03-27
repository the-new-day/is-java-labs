package org.example.dealership.application.mapping;

import org.example.dealership.application.port.in.common.dto.MoneyDto;
import org.example.dealership.domain.model.vo.Money;

public class MoneyMapper {
    public static MoneyDto mapToDto(Money money) {
        return new MoneyDto(money.asBigDecimal());
    }

    public static Money mapFromDto(MoneyDto moneyDto) {
        return new Money(moneyDto.amount());
    }
}
