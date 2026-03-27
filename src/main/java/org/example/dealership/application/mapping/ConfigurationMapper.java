package org.example.dealership.application.mapping;

import org.example.dealership.application.port.in.common.dto.ConfigurationDto;
import org.example.dealership.domain.model.car.CarModel;
import org.example.dealership.domain.model.configuration.ComponentVariantSelection;
import org.example.dealership.domain.model.configuration.Configuration;

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
