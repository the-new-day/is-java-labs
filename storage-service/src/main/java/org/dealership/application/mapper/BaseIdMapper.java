package org.dealership.application.mapper;

import org.dealership.domain.model.id.BrandId;
import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.id.CarModelId;
import org.dealership.domain.model.id.ComponentVariantId;
import org.dealership.domain.model.id.SparePartId;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BaseIdMapper {

    @Named("toBrandId")
    default BrandId toBrandId(UUID id) {
        return new BrandId(id);
    }

    @Named("toCarId")
    default CarId toCarId(UUID id) {
        return new CarId(id);
    }

    @Named("toCarModelId")
    default CarModelId toCarModelId(UUID id) {
        return new CarModelId(id);
    }

    @Named("toComponentVariantId")
    default ComponentVariantId toComponentVariantId(UUID id) {
        return new ComponentVariantId(id);
    }

    @Named("toSparePartId")
    default SparePartId toSparePartId(UUID id) {
        return new SparePartId(id);
    }

    @Named("carModelIdToUuid")
    default UUID carModelIdToUuid(CarModelId id) {
        return id.value();
    }
}
