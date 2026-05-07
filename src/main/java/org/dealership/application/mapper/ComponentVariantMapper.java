package org.dealership.application.mapper;

import org.dealership.application.port.in.common.dto.ComponentVariantDto;
import org.dealership.domain.model.configuration.ComponentVariant;
import org.dealership.domain.model.id.CarModelId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {BaseIdMapper.class, ComponentTypeMapper.class, MoneyMapper.class})
public abstract class ComponentVariantMapper {

    @Autowired
    protected BaseIdMapper baseIdMapper;

    @Autowired
    protected ComponentTypeMapper componentTypeMapper;

    @Autowired
    protected MoneyMapper moneyMapper;

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "type", source = "componentType")
    @Mapping(target = "carModelIds", source = "compatibleModelIds", qualifiedByName = "carModelIdToUuid")
    public abstract ComponentVariantDto toDto(ComponentVariant variant);

    @Named("carModelIdToUuid")
    public UUID carModelIdToUuid(CarModelId id) {
        return id.value();
    }

    public ComponentVariant toDomain(ComponentVariantDto dto) {
        return new ComponentVariant(
                baseIdMapper.toComponentVariantId(dto.id()),
                componentTypeMapper.toDomain(dto.type()),
                dto.name(),
                moneyMapper.toDomain(dto.surcharge()),
                dto.carModelIds().stream()
                        .map(CarModelId::new)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }
}
