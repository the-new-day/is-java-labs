package org.dealership.application.mapping;

import org.dealership.application.port.in.common.dto.ComponentVariantDto;
import org.dealership.domain.model.configuration.ComponentVariant;
import org.dealership.domain.model.id.CarModelId;
import org.dealership.domain.model.id.ComponentVariantId;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ComponentVariantMapper {
    public static ComponentVariantDto mapToDto(ComponentVariant variant) {
        return new ComponentVariantDto(
                variant.getId().value(),
                ComponentTypeMapper.mapToDto(variant.getComponentType()),
                variant.getName(),
                MoneyMapper.mapToDto(variant.getSurcharge()),
                mapModelIdsToDto(variant.getCompatibleModelIds())
        );
    }

    public static ComponentVariant mapFromDto(ComponentVariantDto dto) {
        return new ComponentVariant(
                new ComponentVariantId(dto.id()),
                ComponentTypeMapper.mapFromDto(dto.type()),
                dto.name(),
                MoneyMapper.mapFromDto(dto.surcharge()),
                mapModelIdsFromDto(dto.carModelIds())
        );
    }

    private static Set<UUID> mapModelIdsToDto(Set<CarModelId> modelIds) {
        return modelIds.stream()
                .map(CarModelId::value)
                .collect(Collectors.toUnmodifiableSet());
    }

    private static Set<CarModelId> mapModelIdsFromDto(Set<UUID> modelIds) {
        return modelIds.stream()
                .map(CarModelId::new)
                .collect(Collectors.toUnmodifiableSet());
    }
}
