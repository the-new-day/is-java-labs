package org.dealership.domain.model.carfilter;

import org.dealership.domain.model.enums.*;
import org.dealership.domain.model.id.BrandId;
import org.dealership.domain.model.id.CarModelId;
import org.dealership.domain.model.vo.Money;

public record CarFilter(
        Money minPrice,
        Money maxPrice,
        CarBodyType bodyType,
        BrandId brand,
        Color color,
        CarModelId model,
        DriveType driveType,
        Integer minEnginePower,
        Integer maxEnginePower,
        Double minEngineVolume,
        Double maxEngineVolume,
        FuelType fuelType
) {}
