package org.example.dealership.domain;

import org.example.dealership.domain.exception.DomainValidationException;
import org.example.dealership.domain.model.vo.VinNumber;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VinNumberTest {
    @Test
    void shouldNormalizeAndValidateVin() {
        VinNumber vin = new VinNumber("wba12345678901234");
        assertEquals("WBA12345678901234", vin.getValue());
    }

    @Test
    void shouldRejectInvalidVin() {
        assertThrows(DomainValidationException.class, () -> new VinNumber("123"));
        assertThrows(DomainValidationException.class, () -> new VinNumber("WBA1234567890123I"));
    }
}
