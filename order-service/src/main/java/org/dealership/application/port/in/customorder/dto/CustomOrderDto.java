package org.dealership.application.port.in.customorder.dto;

import org.dealership.application.port.in.common.dto.ConfigurationDto;

import java.util.UUID;

public record CustomOrderDto(
        UUID id,
        UUID clientId,
        UUID managerId,
        ConfigurationDto configuration,
        CustomOrderStatusDto status
) {}
