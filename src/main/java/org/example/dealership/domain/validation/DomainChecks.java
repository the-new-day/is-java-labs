package org.example.dealership.domain.validation;

import org.example.dealership.domain.exception.DomainValidationException;

import java.util.Collection;

public final class DomainChecks {
    private DomainChecks() {
    }

    public static <T> T notNull(T value, String fieldName) {
        if (value == null) {
            throw new DomainValidationException(fieldName + " is required.");
        }
        return value;
    }

    public static String notBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new DomainValidationException(fieldName + " is required.");
        }
        return value;
    }

    public static int positive(int value, String fieldName) {
        if (value <= 0) {
            throw new DomainValidationException(fieldName + " must be positive.");
        }
        return value;
    }

    public static double positive(double value, String fieldName) {
        if (value <= 0) {
            throw new DomainValidationException(fieldName + " must be positive.");
        }
        return value;
    }

    public static <T> Collection<T> notEmpty(Collection<T> value, String fieldName) {
        if (value == null || value.isEmpty()) {
            throw new DomainValidationException(fieldName + " must not be empty.");
        }
        return value;
    }

    public static void require(boolean condition, String message) {
        if (!condition) {
            throw new DomainValidationException(message);
        }
    }
}
