package org.dealership.application.mapper;

import org.dealership.application.port.in.common.dto.ConfigurationDto;
import org.dealership.domain.model.configuration.Configuration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CarModelMapper.class, ComponentVariantSelectionMapper.class})
public interface ConfigurationMapper {

    @Mapping(target = "carModel", source = "model")
    ConfigurationDto toDto(Configuration configuration);

    @Mapping(target = "model", source = "carModel")
    Configuration toDomain(ConfigurationDto dto);
}
