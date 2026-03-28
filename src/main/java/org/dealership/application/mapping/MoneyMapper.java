package org.dealership.application.mapping;

import org.dealership.application.port.in.common.dto.MoneyDto;
import org.dealership.domain.model.vo.Money;

public class MoneyMapper {
    public static MoneyDto mapToDto(Money money) {
        return new MoneyDto(money.asBigDecimal());
    }

    public static Money mapFromDto(MoneyDto moneyDto) {
        return new Money(moneyDto.amount());
    }
}
