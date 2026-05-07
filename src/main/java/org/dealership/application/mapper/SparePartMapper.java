package org.dealership.application.mapper;

import org.dealership.application.port.in.inventory.dto.NewSparePartDto;
import org.dealership.application.port.in.inventory.dto.SparePartSummaryDto;
import org.dealership.domain.model.id.CarModelId;
import org.dealership.domain.model.id.SparePartId;
import org.dealership.domain.model.part.SparePart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {BaseIdMapper.class, MoneyMapper.class})
public interface SparePartMapper {

    @Mapping(target = "id", source = "id.value")
    SparePartSummaryDto toSummaryDto(SparePart part);

    @Mapping(target = "id", source = "id", qualifiedByName = "toSparePartId")
    @Mapping(target = "compatibleModelIds", qualifiedByName = "toCarModelId")
    SparePart toDomain(SparePartSummaryDto dto);

    default SparePart toDomain(NewSparePartDto dto, SparePartId id) {
        return new SparePart(
                id,
                dto.name(),
                moneyMapper().toDomain(dto.price()),
                dto.compatibleModelIds().stream()
                        .map(CarModelId::new)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }

    MoneyMapper moneyMapper();
}
