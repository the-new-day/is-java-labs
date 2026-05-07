package org.dealership.application.mapper;

import org.dealership.application.port.in.inventory.dto.NewSparePartDto;
import org.dealership.application.port.in.inventory.dto.SparePartSummaryDto;
import org.dealership.domain.model.id.CarModelId;
import org.dealership.domain.model.id.SparePartId;
import org.dealership.domain.model.part.SparePart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {BaseIdMapper.class, MoneyMapper.class})
public abstract class SparePartMapper {

    @Autowired
    protected MoneyMapper moneyMapper;

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "compatibleModelIds", source = "compatibleModelIds", qualifiedByName = "carModelIdToUuid")
    public abstract SparePartSummaryDto toSummaryDto(SparePart part);

    @Named("carModelIdToUuid")
    public UUID carModelIdToUuid(CarModelId id) {
        return id.value();
    }

    @Mapping(target = "id", source = "id", qualifiedByName = "toSparePartId")
    @Mapping(target = "compatibleModelIds", source = "compatibleModelIds", qualifiedByName = "toCarModelId")
    public abstract SparePart toDomain(SparePartSummaryDto dto);

    public SparePart toDomain(NewSparePartDto dto, SparePartId id) {
        return new SparePart(
                id,
                dto.name(),
                moneyMapper.toDomain(dto.price()),
                dto.compatibleModelIds().stream()
                        .map(CarModelId::new)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }
}
