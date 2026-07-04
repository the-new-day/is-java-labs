package org.dealership.domain;

import org.dealership.domain.model.vo.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {
    @Test
    void shouldAddSubtractAndCompare() {
        Money base = new Money(BigDecimal.valueOf(100));
        Money extra = new Money(BigDecimal.valueOf(25));

        Money total = base.add(extra);
        assertEquals(new Money(BigDecimal.valueOf(125)), total);

        Money remaining = total.subtract(extra);
        assertEquals(base, remaining);

        assertTrue(total.compareTo(base) > 0);
        assertEquals(0, base.compareTo(new Money(BigDecimal.valueOf(100))));
    }
}
