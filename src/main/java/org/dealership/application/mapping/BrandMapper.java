package org.dealership.application.mapping;

import org.dealership.application.port.in.carcatalog.dto.BrandDto;
import org.dealership.domain.model.car.Brand;
import org.dealership.domain.model.id.BrandId;

public class BrandMapper {
    public static BrandDto mapToDto(Brand brand) {
        return new BrandDto(brand.getId().value(), brand.getName());
    }

    public static Brand mapFromDto(BrandDto brandDto) {
        return new Brand(new BrandId(brandDto.id()), brandDto.name());
    }
}
