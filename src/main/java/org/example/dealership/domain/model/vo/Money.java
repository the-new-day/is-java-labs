package org.example.dealership.domain.model.vo;

import org.example.dealership.domain.validation.DomainChecks;

import java.math.BigDecimal;
import java.util.Objects;

public class Money implements Comparable<Money> {
    public static final Money ZERO = new Money(BigDecimal.ZERO);

    private final BigDecimal amount;

    public Money(BigDecimal amount) {
        this.amount = DomainChecks.notNull(amount, "amount");
    }

    public Money add(Money other) {
        return new Money(amount.add(DomainChecks.notNull(other, "other").amount));
    }

    public Money subtract(Money other) {
        return new Money(amount.subtract(DomainChecks.notNull(other, "other").amount));
    }

    public BigDecimal asBigDecimal() { return amount; }

    @Override
    public int compareTo(Money other) {
        return amount.compareTo(DomainChecks.notNull(other, "other").amount);
    }

    @Override
    public boolean equals(Object o) {
        return o == this || (o instanceof Money m && amount.equals(m.amount));
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return amount.toString();
    }
}
