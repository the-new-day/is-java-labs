package org.dealership.domain;

import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.configuration.ComponentVariant;
import org.dealership.domain.model.configuration.ComponentVariantSelection;
import org.dealership.domain.model.configuration.Configuration;
import org.dealership.domain.model.enums.Color;
import org.dealership.domain.model.enums.ComponentType;
import org.dealership.domain.model.id.BrandId;
import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.id.CarModelId;
import org.dealership.domain.model.vo.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    @Test
    void shouldExposePriceAndToggleAvailability() {
        CarModelId modelId = new CarModelId(UUID.randomUUID());
        ComponentVariant wheels = DomainTestData.variant(ComponentType.WHEELS, modelId, BigDecimal.valueOf(700));
        ComponentVariantSelection selection = DomainTestData.selection(wheels);

        CarModel model = DomainTestData.carModel(
                modelId,
                DomainTestData.brand(new BrandId(UUID.randomUUID())), selection, Set.of(ComponentType.WHEELS)
        );
        Configuration configuration = DomainTestData.configuration(model, selection);

        Car car = DomainTestData.car(
                new CarId(UUID.randomUUID()),
                configuration,
                Color.BLUE,
                false
        );

        assertEquals(new Money(BigDecimal.valueOf(10_700)), car.getPrice());
        assertFalse(car.isTestDriveAvailable());

        Car updated = car.withTestDriveAvailability(true);
        assertTrue(updated.isTestDriveAvailable());
        assertEquals(car.getId(), updated.getId());
    }
}
