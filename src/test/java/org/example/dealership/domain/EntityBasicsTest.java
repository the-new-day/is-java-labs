package org.example.dealership.domain;

import org.example.dealership.domain.model.car.Brand;
import org.example.dealership.domain.model.configuration.ComponentVariant;
import org.example.dealership.domain.model.enums.ComponentType;
import org.example.dealership.domain.model.id.*;
import org.example.dealership.domain.model.part.SparePart;
import org.example.dealership.domain.model.testdrive.TestDriveRequest;
import org.example.dealership.domain.model.user.User;
import org.example.dealership.domain.model.user.UserRole;
import org.example.dealership.domain.model.vo.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EntityBasicsTest {
    @Test
    void shouldExposeBrandAndUserData() {
        BrandId brandId = new BrandId(UUID.randomUUID());
        Brand brand = new Brand(brandId, "BMW");
        assertEquals(brandId, brand.getId());
        assertEquals("BMW", brand.getName());

        UserId userId = new UserId(UUID.randomUUID());
        User user = new User(userId, "Alice", UserRole.CLIENT);
        assertEquals(userId, user.getId());
        assertEquals("Alice", user.getFullName());
        assertEquals(UserRole.CLIENT, user.getRole());
    }

    @Test
    void shouldValidateSparePartCompatibility() {
        CarModelId modelId = new CarModelId(UUID.randomUUID());
        SparePartId partId = new SparePartId(UUID.randomUUID());
        SparePart part = new SparePart(
                partId,
                "Brake pads",
                new Money(BigDecimal.valueOf(250)),
                Set.of(modelId)
        );

        assertEquals(partId, part.getId());
        assertTrue(part.isCompatibleWith(modelId));
        assertEquals(1, part.getCompatibleModelIds().size());
    }

    @Test
    void shouldHandleTestDriveRequestAndVariantCompatibility() {
        TestDriveRequestId requestId = new TestDriveRequestId(UUID.randomUUID());
        UserId clientId = new UserId(UUID.randomUUID());
        CarId carId = new CarId(UUID.randomUUID());
        LocalDateTime startsAt = LocalDateTime.of(2026, 1, 10, 12, 0);

        TestDriveRequest request = new TestDriveRequest(requestId, clientId, carId, startsAt);
        assertEquals(requestId, request.getId());
        assertEquals(clientId, request.getClientId());
        assertEquals(carId, request.getCarId());
        assertEquals(startsAt, request.getStartsAt());

        CarModelId modelId = new CarModelId(UUID.randomUUID());
        ComponentVariant variant = DomainTestData.variant(ComponentType.WHEELS, modelId, BigDecimal.valueOf(100));
        assertTrue(variant.isCompatibleWith(modelId));
    }
}
