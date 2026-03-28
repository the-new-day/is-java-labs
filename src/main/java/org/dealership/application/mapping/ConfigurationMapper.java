package org.dealership.application.mapping;

import org.dealership.application.port.in.common.dto.ConfigurationDto;
import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.configuration.ComponentVariantSelection;
import org.dealership.domain.model.configuration.Configuration;

public class ConfigurationMapper {
    public static ConfigurationDto mapToDto(Configuration configuration) {
        return new ConfigurationDto(
                CarModelMapper.mapToDto(configuration.getModel()),
                ComponentVariantSelectionMapper.mapToDto(configuration.getComponentVariantSelection())
        );
    }

    public static Configuration mapFromDto(ConfigurationDto dto, CarModel model) {
        ComponentVariantSelection selection = ComponentVariantSelectionMapper.mapFromDto(
                dto.componentVariantSelection());
        return new Configuration(model, selection);
    }
}
