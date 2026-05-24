package org.dealership.domain;

import org.dealership.domain.exception.DomainValidationException;
import org.dealership.domain.model.car.Brand;
import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.configuration.ComponentVariant;
import org.dealership.domain.model.configuration.ComponentVariantSelection;
import org.dealership.domain.model.enums.ComponentType;
import org.dealership.domain.model.id.BrandId;
import org.dealership.domain.model.id.CarModelId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CarModelTest {
    @Test
    void shouldReturnFixedComponentsAndConfiguration() {
        BrandId brandId = new BrandId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
        Brand brand = DomainTestData.brand(brandId);
        CarModelId modelId = new CarModelId(UUID.fromString("22222222-2222-2222-2222-222222222222"));

        ComponentVariant wheels = DomainTestData.variant(ComponentType.WHEELS, modelId, BigDecimal.valueOf(500));
        ComponentVariant transmission = DomainTestData.variant(ComponentType.TRANSMISSION, modelId, BigDecimal.valueOf(0));
        ComponentVariantSelection baseSelection = DomainTestData.selection(wheels, transmission);

        CarModel model = DomainTestData.carModel(modelId, brand, baseSelection, Set.of(ComponentType.WHEELS));

        assertTrue(model.isComponentConfigurable(ComponentType.WHEELS));
        assertFalse(model.isComponentConfigurable(ComponentType.TRANSMISSION));
        assertEquals(Set.of(ComponentType.TRANSMISSION), model.getFixedComponentTypes());
        assertEquals(Set.of(ComponentType.TRANSMISSION), model.getFixedConfiguration().getComponentTypes());
    }

    @Test
    void shouldRejectMissingConfigurableTypesInBaseSelection() {
        Brand brand = DomainTestData.brand(new BrandId(UUID.randomUUID()));
        CarModelId modelId = new CarModelId(UUID.randomUUID());

        ComponentVariant wheels = DomainTestData.variant(ComponentType.WHEELS, modelId, BigDecimal.valueOf(500));
        ComponentVariantSelection baseSelection = DomainTestData.selection(wheels);

        assertThrows(DomainValidationException.class, () ->
                DomainTestData.carModel(modelId, brand, baseSelection, Set.of(ComponentType.WHEELS, ComponentType.TRANSMISSION))
        );
    }
}
