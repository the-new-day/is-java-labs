package org.dealership.application.mapper;

import org.dealership.application.port.in.common.dto.ComponentTypeDto;
import org.dealership.application.port.in.common.dto.ComponentVariantDto;
import org.dealership.application.port.in.common.dto.ComponentVariantSelectionDto;
import org.dealership.domain.model.configuration.ComponentVariant;
import org.dealership.domain.model.configuration.ComponentVariantSelection;
import org.dealership.domain.model.enums.ComponentType;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.Map;

@Mapper(componentModel = "spring", uses = {ComponentTypeMapper.class, ComponentVariantMapper.class})
public abstract class ComponentVariantSelectionMapper {

    @Autowired
    protected ComponentTypeMapper componentTypeMapper;

    @Autowired
    protected ComponentVariantMapper componentVariantMapper;

    public ComponentVariantSelectionDto toDto(ComponentVariantSelection selection) {
        Map<ComponentTypeDto, ComponentVariantDto> map = new LinkedHashMap<>();
        for (Map.Entry<ComponentType, ComponentVariant> entry : selection.asMap().entrySet()) {
            map.put(componentTypeMapper.toDto(entry.getKey()), componentVariantMapper.toDto(entry.getValue()));
        }
        return new ComponentVariantSelectionDto(map);
    }

    public ComponentVariantSelection toDomain(ComponentVariantSelectionDto dto) {
        Map<ComponentType, ComponentVariant> map = new LinkedHashMap<>();
        for (Map.Entry<ComponentTypeDto, ComponentVariantDto> entry : dto.selection().entrySet()) {
            map.put(componentTypeMapper.toDomain(entry.getKey()), componentVariantMapper.toDomain(entry.getValue()));
        }
        return new ComponentVariantSelection(map);
    }
}
