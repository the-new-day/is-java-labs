package org.dealership.domain;

import org.dealership.domain.model.car.Brand;
import org.dealership.domain.model.configuration.ComponentVariant;
import org.dealership.domain.model.enums.ComponentType;
import org.dealership.domain.model.id.*;
import org.dealership.domain.model.part.SparePart;
import org.dealership.domain.model.vo.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EntityBasicsTest {
    @Test
    void shouldExposeBrandData() {
        BrandId brandId = new BrandId(UUID.randomUUID());
        Brand brand = new Brand(brandId, "BMW");
        assertEquals(brandId, brand.getId());
        assertEquals("BMW", brand.getName());
    }

    @Test
    void shouldValidateSparePartCompatibility() {
        CarModelId modelId = new CarModelId(UUID.randomUUID());
        SparePartId partId = new SparePartId(UUID.randomUUID());
        SparePart part = new SparePart(
                partId, "Brake pads",
                new Money(BigDecimal.valueOf(250)),
                Set.of(modelId)
        );

        assertEquals(partId, part.getId());
        assertTrue(part.isCompatibleWith(modelId));
        assertEquals(1, part.getCompatibleModelIds().size());
    }

    @Test
    void shouldHandleVariantCompatibility() {
        CarModelId modelId = new CarModelId(UUID.randomUUID());
        ComponentVariant variant = DomainTestData.variant(ComponentType.WHEELS, modelId, BigDecimal.valueOf(100));
        assertTrue(variant.isCompatibleWith(modelId));
    }
}
