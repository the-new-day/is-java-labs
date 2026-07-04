package org.dealership.application.mapper;

import org.dealership.application.port.in.carcatalog.dto.BrandDto;
import org.dealership.domain.model.car.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BaseIdMapper.class})
public interface BrandMapper {
    @Mapping(target = "id", source = "id.value")
    BrandDto toDto(Brand brand);

    @Mapping(target = "id", source = "id", qualifiedByName = "toBrandId")
    Brand toDomain(BrandDto dto);
}