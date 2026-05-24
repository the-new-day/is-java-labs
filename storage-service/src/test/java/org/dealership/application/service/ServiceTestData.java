package org.dealership.application.service;

import org.dealership.application.port.in.common.dto.*;
import org.dealership.application.port.in.inventory.dto.*;
import org.dealership.domain.model.car.Brand;
import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.configuration.ComponentVariantSelection;
import org.dealership.domain.model.configuration.Configuration;
import org.dealership.domain.model.enums.*;
import org.dealership.domain.model.id.*;
import org.dealership.domain.model.part.SparePart;
import org.dealership.domain.model.vo.Money;
import org.dealership.domain.model.vo.VinNumber;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class ServiceTestData {
    private ServiceTestData() {
    }

    public static Brand brand(UUID id) {
        return new Brand(new BrandId(id), "Brand");
    }

    public static CarModel carModel(UUID modelId, Brand brand) {
        return new CarModel(
                new CarModelId(modelId), brand, "Model",
                new Money(BigDecimal.valueOf(10_000)),
                CarBodyType.SEDAN, FuelType.PETROL, DriveType.REAR,
                2.0, 200, TransmissionType.MANUAL,
                ComponentVariantSelection.empty(), Set.of()
        );
    }

    public static Configuration configuration(CarModel model) {
        return new Configuration(model, ComponentVariantSelection.empty());
    }

    public static Car car(UUID carId, CarModel model) {
        return new Car(
                new CarId(carId),
                new VinNumber("WBA12345678901234"),
                configuration(model),
                Color.BLACK,
                true
        );
    }

    public static CarModelDto carModelDto(UUID id) {
        return new CarModelDto(
                id,
                new org.dealership.application.port.in.carcatalog.dto.BrandDto(UUID.randomUUID(), "Brand"),
                "Model",
                new MoneyDto(BigDecimal.valueOf(10_000)),
                new CarBodyTypeDto(CarBodyType.SEDAN.name()),
                new FuelTypeDto(FuelType.PETROL.name()),
                new DriveTypeDto(DriveType.REAR.name()),
                2.0, 200,
                new TransmissionTypeDto(TransmissionType.MANUAL.name()),
                new ComponentVariantSelectionDto(Map.of()),
                Set.of()
        );
    }

    public static ConfigurationDto configurationDto(UUID modelId) {
        return new ConfigurationDto(carModelDto(modelId), new ComponentVariantSelectionDto(Map.of()));
    }

    public static NewCarDetailsDto newCarDetailsDto(UUID modelId) {
        return new NewCarDetailsDto(
                new VinNumberDto("WBA12345678901234"),
                configurationDto(modelId),
                new ColorDto(Color.BLACK.name())
        );
    }

    public static CarDetailsDto carDetailsDto(UUID carId, UUID modelId, boolean testDriveAvailable) {
        return new CarDetailsDto(
                carId,
                configurationDto(modelId),
                new MoneyDto(BigDecimal.valueOf(10_000)),
                new ColorDto(Color.BLACK.name()),
                testDriveAvailable
        );
    }

    public static NewModelDto newModelDto(UUID brandId) {
        return new NewModelDto(
                brandId, "Model",
                new MoneyDto(BigDecimal.valueOf(10_000)),
                new CarBodyTypeDto(CarBodyType.SEDAN.name()),
                new FuelTypeDto(FuelType.PETROL.name()),
                new DriveTypeDto(DriveType.REAR.name()),
                2.0, 200,
                new TransmissionTypeDto(TransmissionType.MANUAL.name()),
                new ComponentVariantSelectionDto(Map.of()),
                Set.of()
        );
    }

    public static NewSparePartDto newSparePartDto(UUID modelId) {
        return new NewSparePartDto("Part", new MoneyDto(BigDecimal.valueOf(500)), Set.of(modelId));
    }

    public static SparePartSummaryDto sparePartSummary(UUID partId, UUID modelId) {
        return new SparePartSummaryDto(partId, "Part", new MoneyDto(BigDecimal.valueOf(500)), Set.of(modelId));
    }

    public static SparePart sparePart(UUID partId, UUID modelId) {
        return new SparePart(
                new SparePartId(partId), "Part",
                new Money(BigDecimal.valueOf(500)),
                Set.of(new CarModelId(modelId))
        );
    }
}
