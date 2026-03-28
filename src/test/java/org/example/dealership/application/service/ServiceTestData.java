package org.example.dealership.application.service;

import org.example.dealership.application.port.in.carcatalog.dto.BrandDto;
import org.example.dealership.application.port.in.carcatalog.dto.CarFilterDto;
import org.example.dealership.application.port.in.common.dto.*;
import org.example.dealership.application.port.in.customorder.dto.CustomOrderDto;
import org.example.dealership.application.port.in.customorder.dto.CustomOrderStatusDto;
import org.example.dealership.application.port.in.inventory.dto.NewCarDetailsDto;
import org.example.dealership.application.port.in.inventory.dto.NewModelDto;
import org.example.dealership.application.port.in.inventory.dto.NewSparePartDto;
import org.example.dealership.application.port.in.inventory.dto.SparePartSummary;
import org.example.dealership.application.port.in.stockorder.dto.StockOrderDto;
import org.example.dealership.application.port.in.stockorder.dto.StockOrderStatusDto;
import org.example.dealership.application.port.in.testdrive.dto.TestDriveRequestDto;
import org.example.dealership.application.port.in.user.dto.UserDto;
import org.example.dealership.application.port.in.user.dto.UserRoleDto;
import org.example.dealership.domain.model.car.Brand;
import org.example.dealership.domain.model.car.Car;
import org.example.dealership.domain.model.car.CarModel;
import org.example.dealership.domain.model.configuration.ComponentVariantSelection;
import org.example.dealership.domain.model.configuration.Configuration;
import org.example.dealership.domain.model.enums.*;
import org.example.dealership.domain.model.id.*;
import org.example.dealership.domain.model.order.CustomCarOrder;
import org.example.dealership.domain.model.order.StockCarOrder;
import org.example.dealership.domain.model.order.state.CustomCarOrderStatus;
import org.example.dealership.domain.model.order.state.StockCarOrderStatus;
import org.example.dealership.domain.model.part.SparePart;
import org.example.dealership.domain.model.testdrive.TestDriveRequest;
import org.example.dealership.domain.model.user.User;
import org.example.dealership.domain.model.user.UserRole;
import org.example.dealership.domain.model.vo.Money;
import org.example.dealership.domain.model.vo.VinNumber;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
                new CarModelId(modelId),
                brand,
                "Model",
                new Money(BigDecimal.valueOf(10_000)),
                CarBodyType.SEDAN,
                FuelType.PETROL,
                DriveType.REAR,
                2.0,
                200,
                TransmissionType.MANUAL,
                ComponentVariantSelection.empty(),
                Set.of()
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

    public static BrandDto brandDto(UUID id) {
        return new BrandDto(id, "Brand");
    }

    public static CarModelDto carModelDto(UUID id) {
        return new CarModelDto(
                id,
                brandDto(UUID.randomUUID()),
                "Model",
                new MoneyDto(BigDecimal.valueOf(10_000)),
                new CarBodyTypeDto(CarBodyType.SEDAN.name()),
                new FuelTypeDto(FuelType.PETROL.name()),
                new DriveTypeDto(DriveType.REAR.name()),
                2.0,
                200,
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

    public static CarFilterDto carFilterDto(UUID brandId, UUID modelId) {
        return new CarFilterDto(
                new MoneyDto(BigDecimal.valueOf(5_000)),
                new MoneyDto(BigDecimal.valueOf(20_000)),
                brandId,
                modelId,
                new CarBodyTypeDto(CarBodyType.SEDAN.name()),
                new FuelTypeDto(FuelType.PETROL.name()),
                150,
                250,
                1.5,
                3.0,
                new TransmissionTypeDto(TransmissionType.MANUAL.name()),
                new DriveTypeDto(DriveType.REAR.name()),
                new ColorDto(Color.BLACK.name())
        );
    }

    public static StockOrderDto stockOrderDto(
            UUID orderId,
            UUID clientId,
            UUID managerId,
            UUID carId,
            String status
    ) {
        return new StockOrderDto(orderId, clientId, managerId, carId, new StockOrderStatusDto(status));
    }

    public static CustomOrderDto customOrderDto(
            UUID orderId,
            UUID clientId,
            UUID managerId,
            UUID carId,
            String status
    ) {
        return new CustomOrderDto(orderId, clientId, managerId, carId, new CustomOrderStatusDto(status));
    }

    public static TestDriveRequestDto testDriveRequestDto(
            UUID id,
            UUID clientId,
            UUID carId,
            LocalDateTime startsAt
    ) {
        return new TestDriveRequestDto(id, clientId, carId, startsAt);
    }

    public static UserDto userDto(UUID id, String fullName, String role) {
        return new UserDto(id, fullName, new UserRoleDto(role));
    }

    public static NewModelDto newModelDto(UUID brandId) {
        return new NewModelDto(
                brandId,
                "Model",
                new MoneyDto(BigDecimal.valueOf(10_000)),
                new CarBodyTypeDto(CarBodyType.SEDAN.name()),
                new FuelTypeDto(FuelType.PETROL.name()),
                new DriveTypeDto(DriveType.REAR.name()),
                2.0,
                200,
                new TransmissionTypeDto(TransmissionType.MANUAL.name()),
                new ComponentVariantSelectionDto(Map.of()),
                Set.of()
        );
    }

    public static NewSparePartDto newSparePartDto(UUID modelId) {
        return new NewSparePartDto(
                "Part",
                new MoneyDto(BigDecimal.valueOf(500)),
                Set.of(modelId)
        );
    }

    public static SparePartSummary sparePartSummary(UUID partId, UUID modelId) {
        return new SparePartSummary(
                partId,
                "Part",
                new MoneyDto(BigDecimal.valueOf(500)),
                Set.of(modelId)
        );
    }

    public static StockCarOrder stockOrder(UUID orderId, UUID clientId, UUID managerId, UUID carId) {
        return new StockCarOrder(
                new OrderId(orderId),
                new UserId(clientId),
                new UserId(managerId),
                new CarId(carId),
                StockCarOrderStatus.PLACED
        );
    }

    public static CustomCarOrder customOrder(UUID orderId, UUID clientId, UUID managerId, CarModel model) {
        return new CustomCarOrder(
                new OrderId(orderId),
                new UserId(clientId),
                new UserId(managerId),
                configuration(model),
                CustomCarOrderStatus.PLACED
        );
    }

    public static TestDriveRequest testDriveRequest(UUID id, UUID clientId, UUID carId, LocalDateTime startsAt) {
        return new TestDriveRequest(
                new TestDriveRequestId(id),
                new UserId(clientId),
                new CarId(carId),
                startsAt
        );
    }

    public static SparePart sparePart(UUID partId, UUID modelId) {
        return new SparePart(
                new SparePartId(partId),
                "Part",
                new Money(BigDecimal.valueOf(500)),
                Set.of(new CarModelId(modelId))
        );
    }

    public static User user(UUID userId) {
        return user(userId, UserRole.CLIENT);
    }

    public static User user(UUID userId, UserRole role) {
        return new User(new UserId(userId), "Full Name", role);
    }
}
