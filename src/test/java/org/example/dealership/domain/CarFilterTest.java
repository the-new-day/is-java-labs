package org.example.dealership.domain;

import org.example.dealership.domain.model.car.Brand;
import org.example.dealership.domain.model.car.Car;
import org.example.dealership.domain.model.car.CarModel;
import org.example.dealership.domain.model.carfilter.CarFilter;
import org.example.dealership.domain.model.configuration.ComponentVariant;
import org.example.dealership.domain.model.configuration.ComponentVariantSelection;
import org.example.dealership.domain.model.configuration.Configuration;
import org.example.dealership.domain.model.enums.*;
import org.example.dealership.domain.model.id.BrandId;
import org.example.dealership.domain.model.id.CarId;
import org.example.dealership.domain.model.id.CarModelId;
import org.example.dealership.domain.model.vo.Money;
import org.example.dealership.domain.model.vo.VinNumber;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CarFilterTest {
    @Test
    void shouldMatchAllCriteria() {
        BrandId brandId = new BrandId(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"));
        Brand brand = DomainTestData.brand(brandId);
        CarModelId modelId = new CarModelId(UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"));

        ComponentVariant wheels = DomainTestData.variant(ComponentType.WHEELS, modelId, BigDecimal.valueOf(500));
        ComponentVariant transmission = DomainTestData.variant(
                ComponentType.TRANSMISSION, modelId, BigDecimal.valueOf(0));
        ComponentVariantSelection selection = DomainTestData.selection(wheels, transmission);

        CarModel model = new CarModel(
                modelId,
                brand,
                "Roadster",
                new Money(BigDecimal.valueOf(20_000)),
                CarBodyType.COUPE,
                FuelType.PETROL,
                DriveType.REAR,
                3.0,
                320,
                TransmissionType.MANUAL,
                selection,
                Set.of(ComponentType.WHEELS)
        );
        Configuration configuration = new Configuration(model, selection);
        Car car = new Car(new CarId(UUID.randomUUID()),
                new VinNumber("WBA12345678901234"),
                configuration,
                Color.BLACK,
                true
        );

        CarFilter filter = CarFilter.builder()
                .priceRange(new Money(BigDecimal.valueOf(10_000)), new Money(BigDecimal.valueOf(25_000)))
                .bodyType(CarBodyType.COUPE)
                .driveType(DriveType.REAR)
                .fuelType(FuelType.PETROL)
                .transmissionType(TransmissionType.MANUAL)
                .brand(brandId)
                .model(modelId)
                .color(Color.BLACK)
                .enginePower(300, 350)
                .engineVolume(2.5, 3.5)
                .build();

        assertTrue(filter.matches(car));
    }

    @Test
    void shouldRejectWhenAnyCriteriaFails() {
        BrandId brandId = new BrandId(UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc"));
        Brand brand = DomainTestData.brand(brandId);
        CarModelId modelId = new CarModelId(UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd"));

        ComponentVariant wheels = DomainTestData.variant(ComponentType.WHEELS, modelId, BigDecimal.valueOf(100));
        ComponentVariantSelection selection = DomainTestData.selection(wheels);

        CarModel model = DomainTestData.carModel(modelId, brand, selection, Set.of(ComponentType.WHEELS));
        Configuration configuration = DomainTestData.configuration(model, selection);
        Car car = DomainTestData.car(new CarId(UUID.randomUUID()), configuration, Color.WHITE, true);

        CarFilter filter = CarFilter.builder()
                .color(Color.BLACK)
                .build();

        assertFalse(filter.matches(car));
    }
}
