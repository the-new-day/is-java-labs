package org.example.dealership.domain;

import org.example.dealership.domain.exception.DomainValidationException;
import org.example.dealership.domain.exception.IncompatibleComponentException;
import org.example.dealership.domain.model.car.Brand;
import org.example.dealership.domain.model.car.CarModel;
import org.example.dealership.domain.model.configuration.ComponentVariant;
import org.example.dealership.domain.model.configuration.ComponentVariantSelection;
import org.example.dealership.domain.model.configuration.Configuration;
import org.example.dealership.domain.model.enums.ComponentType;
import org.example.dealership.domain.model.id.BrandId;
import org.example.dealership.domain.model.id.CarModelId;
import org.example.dealership.domain.model.id.ComponentVariantId;
import org.example.dealership.domain.model.vo.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {
    @Test
    void shouldCalculatePriceForConfigurableVariants() {
        Brand brand = DomainTestData.brand(new BrandId(UUID.randomUUID()));
        CarModelId modelId = new CarModelId(UUID.randomUUID());

        ComponentVariant wheels = DomainTestData.variant(ComponentType.WHEELS, modelId, BigDecimal.valueOf(500));
        ComponentVariant transmission = DomainTestData.variant(
                ComponentType.TRANSMISSION, modelId, BigDecimal.valueOf(200));
        ComponentVariantSelection baseSelection = DomainTestData.selection(wheels, transmission);

        CarModel model = DomainTestData.carModel(modelId, brand, baseSelection, Set.of(ComponentType.WHEELS));
        Configuration configuration = DomainTestData.configuration(model, baseSelection);

        Money price = configuration.getPrice();
        assertEquals(new Money(BigDecimal.valueOf(10_500)), price);
    }

    @Test
    void shouldThrowWhenMissingRequiredComponents() {
        Brand brand = DomainTestData.brand(new BrandId(UUID.randomUUID()));
        CarModelId modelId = new CarModelId(UUID.randomUUID());

        ComponentVariant wheels = DomainTestData.variant(ComponentType.WHEELS, modelId, BigDecimal.valueOf(500));
        ComponentVariant transmission = DomainTestData.variant(
                ComponentType.TRANSMISSION, modelId, BigDecimal.valueOf(200));
        ComponentVariantSelection baseSelection = DomainTestData.selection(wheels, transmission);

        CarModel model = DomainTestData.carModel(modelId, brand, baseSelection, Set.of(ComponentType.WHEELS));
        Configuration configuration = DomainTestData.configuration(model, DomainTestData.selection(wheels));

        assertThrows(DomainValidationException.class, configuration::getPrice);
    }

    @Test
    void shouldRejectUnsupportedOrFixedComponentTypes() {
        Brand brand = DomainTestData.brand(new BrandId(UUID.randomUUID()));
        CarModelId modelId = new CarModelId(UUID.randomUUID());

        ComponentVariant wheels = DomainTestData.variant(ComponentType.WHEELS, modelId, BigDecimal.valueOf(500));
        ComponentVariant transmission = DomainTestData.variant(
                ComponentType.TRANSMISSION, modelId, BigDecimal.valueOf(200));
        ComponentVariantSelection baseSelection = DomainTestData.selection(wheels, transmission);

        CarModel model = DomainTestData.carModel(modelId, brand, baseSelection, Set.of(ComponentType.WHEELS));
        Configuration configuration = DomainTestData.configuration(model, baseSelection);

        ComponentVariant unsupported = DomainTestData.variant(
                ComponentType.INTERIOR, modelId, BigDecimal.valueOf(100));
        assertThrows(DomainValidationException.class, () -> configuration.withVariant(unsupported));

        ComponentVariant fixed = DomainTestData.variant(
                ComponentType.TRANSMISSION, modelId, BigDecimal.valueOf(50));
        assertThrows(DomainValidationException.class, () -> configuration.withVariant(fixed));
    }

    @Test
    void shouldRejectIncompatibleVariant() {
        Brand brand = DomainTestData.brand(new BrandId(UUID.randomUUID()));
        CarModelId modelId = new CarModelId(UUID.randomUUID());

        ComponentVariant wheels = DomainTestData.variant(ComponentType.WHEELS, modelId, BigDecimal.valueOf(500));
        ComponentVariantSelection baseSelection = DomainTestData.selection(wheels);

        CarModel model = DomainTestData.carModel(modelId, brand, baseSelection, Set.of(ComponentType.WHEELS));
        Configuration configuration = DomainTestData.configuration(model, baseSelection);

        CarModelId otherModelId = new CarModelId(UUID.randomUUID());
        ComponentVariant incompatible = new ComponentVariant(
                new ComponentVariantId(UUID.randomUUID()),
                ComponentType.WHEELS,
                "Other",
                new Money(BigDecimal.valueOf(100)),
                Set.of(otherModelId)
        );

        assertThrows(IncompatibleComponentException.class, () -> configuration.withVariant(incompatible));
    }
}
