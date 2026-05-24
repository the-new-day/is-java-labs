package org.dealership.domain;

import org.dealership.domain.model.car.Brand;
import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.configuration.ComponentVariant;
import org.dealership.domain.model.configuration.ComponentVariantSelection;
import org.dealership.domain.model.configuration.Configuration;
import org.dealership.domain.model.enums.*;
import org.dealership.domain.model.id.BrandId;
import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.id.CarModelId;
import org.dealership.domain.model.id.ComponentVariantId;
import org.dealership.domain.model.vo.Money;
import org.dealership.domain.model.vo.VinNumber;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

final class DomainTestData {
    private DomainTestData() {
    }

    static Brand brand(BrandId id) {
        return new Brand(id, "Brand");
    }

    static BrandId brandId(String value) {
        return new BrandId(UUID.fromString(value));
    }

    static CarModelId modelId(String value) {
        return new CarModelId(UUID.fromString(value));
    }

    static ComponentVariant variant(ComponentType type, CarModelId modelId, BigDecimal surcharge) {
        return new ComponentVariant(
                new ComponentVariantId(UUID.randomUUID()),
                type,
                type.name(),
                new Money(surcharge),
                Set.of(modelId)
        );
    }

    static ComponentVariantSelection selection(ComponentVariant... variants) {
        Map<ComponentType, ComponentVariant> map = new EnumMap<>(ComponentType.class);
        for (ComponentVariant variant : variants) {
            map.put(variant.getComponentType(), variant);
        }
        return new ComponentVariantSelection(map);
    }

    static CarModel carModel(CarModelId id, Brand brand, ComponentVariantSelection baseSelection, Set<ComponentType> configurableTypes) {
        return new CarModel(
                id, brand, "Model",
                new Money(BigDecimal.valueOf(10_000)),
                CarBodyType.SEDAN, FuelType.PETROL, DriveType.REAR,
                2.0, 200, TransmissionType.MANUAL,
                baseSelection, configurableTypes
        );
    }

    static Configuration configuration(CarModel model, ComponentVariantSelection selection) {
        return new Configuration(model, selection);
    }

    static Car car(CarId id, Configuration configuration, Color color, boolean testDriveAvailable) {
        return new Car(id, new VinNumber("WBA12345678901234"), configuration, color, testDriveAvailable);
    }
}
