package org.dealership.application.mapper;

import org.dealership.application.port.in.common.dto.ComponentVariantSelectionDto;
import org.dealership.domain.model.configuration.ComponentVariantSelection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ComponentTypeMapper.class, ComponentVariantMapper.class})
public interface ComponentVariantSelectionMapper {

    @Mapping(target = "selection", source = "asMap")
    ComponentVariantSelectionDto toDto(ComponentVariantSelection selection);

    @Mapping(target = "variants", source = "selection")
    ComponentVariantSelection toDomain(ComponentVariantSelectionDto dto);
}
