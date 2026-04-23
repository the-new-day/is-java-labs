package org.dealership.infrastructure.persistence.jpa.mapper;

import org.dealership.domain.model.car.Brand;
import org.dealership.infrastructure.persistence.jpa.entity.BrandJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = IdMapper.class)
public interface BrandJpaMapper {
    @Mapping(target = "id", source = "id", qualifiedByName = "toBrandId")
    Brand toDomain(BrandJpaEntity entity);

    @Mapping(target = "id", source = "id", qualifiedByName = "toUuidFromBrandId")
    BrandJpaEntity toEntity(Brand brand);
}
