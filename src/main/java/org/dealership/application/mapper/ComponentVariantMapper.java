package org.dealership.application.mapper;

import org.dealership.application.port.in.common.dto.ComponentVariantDto;
import org.dealership.domain.model.configuration.ComponentVariant;
import org.dealership.domain.model.id.CarModelId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {
        BaseIdMapper.class,
        ComponentTypeMapper.class,
        MoneyMapper.class
})
public interface ComponentVariantMapper {

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "type", source = "componentType")
    ComponentVariantDto toDto(ComponentVariant variant);

    default ComponentVariant toDomain(ComponentVariantDto dto) {
        return new ComponentVariant(
                baseIdMapper().toComponentVariantId(dto.id()),
                componentTypeMapper().toDomain(dto.type()),
                dto.name(),
                moneyMapper().toDomain(dto.surcharge()),
                dto.carModelIds().stream()
                        .map(CarModelId::new)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }

    BaseIdMapper baseIdMapper();
    ComponentTypeMapper componentTypeMapper();
    MoneyMapper moneyMapper();
}
