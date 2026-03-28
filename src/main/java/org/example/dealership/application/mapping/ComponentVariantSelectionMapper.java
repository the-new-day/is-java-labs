package org.example.dealership.application.mapping;

import org.example.dealership.application.port.in.common.dto.ComponentVariantDto;
import org.example.dealership.application.port.in.common.dto.ComponentVariantSelectionDto;
import org.example.dealership.application.port.in.common.dto.ComponentTypeDto;
import org.example.dealership.domain.model.configuration.ComponentVariant;
import org.example.dealership.domain.model.configuration.ComponentVariantSelection;
import org.example.dealership.domain.model.enums.ComponentType;

import java.util.Map;
import java.util.stream.Collectors;

public class ComponentVariantSelectionMapper {
    public static ComponentVariantSelectionDto mapToDto(ComponentVariantSelection selection) {
        Map<ComponentTypeDto, ComponentVariantDto> map =
                selection.asMap().entrySet().stream()
                        .collect(Collectors.toUnmodifiableMap(
                                entry -> ComponentTypeMapper.mapToDto(entry.getKey()),
                                entry -> ComponentVariantMapper.mapToDto(entry.getValue())
                        ));
        return new ComponentVariantSelectionDto(map);
    }

    public static ComponentVariantSelection mapFromDto(ComponentVariantSelectionDto dto) {
        Map<ComponentType, ComponentVariant> map =
                dto.selection().entrySet().stream()
                        .collect(Collectors.toUnmodifiableMap(
                                entry -> ComponentTypeMapper.mapFromDto(entry.getKey()),
                                entry -> ComponentVariantMapper.mapFromDto(entry.getValue())
                        ));
        return new ComponentVariantSelection(map);
    }
}
