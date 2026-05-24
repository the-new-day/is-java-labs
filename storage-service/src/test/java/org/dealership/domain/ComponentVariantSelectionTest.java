package org.dealership.domain;

import org.dealership.domain.model.configuration.ComponentVariant;
import org.dealership.domain.model.configuration.ComponentVariantSelection;
import org.dealership.domain.model.enums.ComponentType;
import org.dealership.domain.model.id.CarModelId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ComponentVariantSelectionTest {
    @Test
    void shouldFilterByTypesAndReportMissing() {
        CarModelId modelId = new CarModelId(UUID.randomUUID());
        ComponentVariant wheels = DomainTestData.variant(ComponentType.WHEELS, modelId, BigDecimal.valueOf(100));
        ComponentVariant transmission = DomainTestData.variant(ComponentType.TRANSMISSION, modelId, BigDecimal.valueOf(200));
        ComponentVariantSelection selection = DomainTestData.selection(wheels, transmission);

        ComponentVariantSelection filtered = selection.filterByTypes(Set.of(ComponentType.WHEELS));
        assertEquals(Set.of(ComponentType.WHEELS), filtered.getComponentTypes());

        assertTrue(selection.isComplete(Set.of(ComponentType.WHEELS, ComponentType.TRANSMISSION)));
        assertEquals(
                Set.of(ComponentType.STEERING_WHEEL),
                selection.missingTypes(Set.of(ComponentType.WHEELS, ComponentType.STEERING_WHEEL))
        );
    }

    @Test
    void shouldAddVariantWithVariant() {
        CarModelId modelId = new CarModelId(UUID.randomUUID());
        ComponentVariant wheels = DomainTestData.variant(ComponentType.WHEELS, modelId, BigDecimal.valueOf(100));
        ComponentVariantSelection selection = DomainTestData.selection(wheels);

        ComponentVariant interior = DomainTestData.variant(ComponentType.INTERIOR, modelId, BigDecimal.valueOf(300));
        ComponentVariantSelection updated = selection.withVariant(ComponentType.INTERIOR, interior);

        assertTrue(updated.hasType(ComponentType.INTERIOR));
        assertEquals(2, updated.getVariants().size());
    }
}
