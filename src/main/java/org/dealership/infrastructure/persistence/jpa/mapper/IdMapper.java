package org.dealership.infrastructure.persistence.jpa.mapper;

import org.dealership.domain.model.id.*;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public class IdMapper {
    @Named("toBrandId")
    public BrandId toBrandId(UUID id) {
        return id != null ? new BrandId(id) : null;
    }

    @Named("toUuidFromBrandId")
    public UUID toUuidFromBrandId(BrandId id) {
        return id != null ? id.value() : null;
    }

    @Named("toCarId")
    public CarId toCarId(UUID id) {
        return id != null ? new CarId(id) : null;
    }

    @Named("toUuidFromCarId")
    public UUID toUuidFromCarId(CarId id) {
        return id != null ? id.value() : null;
    }

    @Named("toCarModelId")
    public CarModelId toCarModelId(UUID id) {
        return id != null ? new CarModelId(id) : null;
    }

    @Named("toUuidFromCarModelId")
    public UUID toUuidFromCarModelId(CarModelId id) {
        return id != null ? id.value() : null;
    }

    @Named("toComponentVariantId")
    public ComponentVariantId toComponentVariantId(UUID id) {
        return id != null ? new ComponentVariantId(id) : null;
    }

    @Named("toUuidFromComponentVariantId")
    public UUID toUuidFromComponentVariantId(ComponentVariantId id) {
        return id != null ? id.value() : null;
    }

    @Named("toOrderId")
    public OrderId toOrderId(UUID id) {
        return id != null ? new OrderId(id) : null;
    }

    @Named("toUuidFromOrderId")
    public UUID toUuidFromOrderId(OrderId id) {
        return id != null ? id.value() : null;
    }

    @Named("toSparePartId")
    public SparePartId toSparePartId(UUID id) {
        return id != null ? new SparePartId(id) : null;
    }

    @Named("toUuidFromSparePartId")
    public UUID toUuidFromSparePartId(SparePartId id) {
        return id != null ? id.value() : null;
    }

    @Named("toTestDriveRequestId")
    public TestDriveRequestId toTestDriveRequestId(UUID id) {
        return id != null ? new TestDriveRequestId(id) : null;
    }

    @Named("toUuidFromTestDriveRequestId")
    public UUID toUuidFromTestDriveRequestId(TestDriveRequestId id) {
        return id != null ? id.value() : null;
    }

    @Named("toUserId")
    public UserId toUserId(UUID id) {
        return id != null ? new UserId(id) : null;
    }

    @Named("toUuidFromUserId")
    public UUID toUuidFromUserId(UserId id) {
        return id != null ? id.value() : null;
    }
}