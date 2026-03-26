package org.example.dealership.domain.model.vo;

import org.example.dealership.domain.validation.DomainChecks;

import java.util.regex.Pattern;

public class VinNumber {
    private static final Pattern VIN_PATTERN = Pattern.compile("^[A-HJ-NPR-Z0-9]{17}$");

    private final String value;

    public VinNumber(String value) {
        String normalized = DomainChecks.notBlank(value, "vin")
                .trim()
                .toUpperCase();
        DomainChecks.require(VIN_PATTERN.matcher(normalized).matches(), "Invalid VIN format.");
        this.value = normalized;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || (obj instanceof VinNumber vn && value.equals(vn.value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
