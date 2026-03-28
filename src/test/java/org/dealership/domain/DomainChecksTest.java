package org.dealership.domain;

import org.dealership.domain.exception.DomainValidationException;
import org.dealership.domain.validation.DomainChecks;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DomainChecksTest {
    @Test
    void shouldValidateRequiredValues() {
        String value = DomainChecks.notBlank("ok", "field");
        assertEquals("ok", value);

        int positive = DomainChecks.positive(10, "amount");
        assertEquals(10, positive);

        var list = DomainChecks.notEmpty(List.of("a"), "list");
        assertEquals(1, list.size());
    }

    @Test
    void shouldThrowOnInvalidValues() {
        assertThrows(DomainValidationException.class, () -> DomainChecks.notNull(null, "x"));
        assertThrows(DomainValidationException.class, () -> DomainChecks.notBlank(" ", "x"));
        assertThrows(DomainValidationException.class, () -> DomainChecks.positive(0, "x"));
        assertThrows(DomainValidationException.class, () -> DomainChecks.notEmpty(List.of(), "x"));
        assertThrows(DomainValidationException.class, () -> DomainChecks.require(false, "bad"));
    }
}
