package org.dealership.application.port.in.common.dto;

import java.util.Map;

public record ComponentVariantSelectionDto(Map<ComponentTypeDto, ComponentVariantDto> selection) {}
