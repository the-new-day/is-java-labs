package org.example.dealership.application.mapping;

import org.example.dealership.application.port.in.inventory.dto.NewSparePartDto;
import org.example.dealership.application.port.in.inventory.dto.SparePartSummary;
import org.example.dealership.domain.model.id.CarModelId;
import org.example.dealership.domain.model.id.SparePartId;
import org.example.dealership.domain.model.part.SparePart;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class SparePartMapper {
    public static SparePartSummary mapToSummaryDto(SparePart part) {
        return new SparePartSummary(
                part.getId().value(),
                part.getName(),
                MoneyMapper.mapToDto(part.getPrice()),
                mapModelIdsToDto(part.getCompatibleModelIds())
        );
    }

    public static SparePart mapFromDto(SparePartSummary dto) {
        return new SparePart(
                new SparePartId(dto.id()),
                dto.name(),
                MoneyMapper.mapFromDto(dto.price()),
                mapModelIdsFromDto(dto.compatibleModelIds())
        );
    }

    public static SparePart mapFromNewDto(NewSparePartDto dto, SparePartId id) {
        return new SparePart(
                id,
                dto.name(),
                MoneyMapper.mapFromDto(dto.price()),
                mapModelIdsFromDto(dto.compatibleModelIds())
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
