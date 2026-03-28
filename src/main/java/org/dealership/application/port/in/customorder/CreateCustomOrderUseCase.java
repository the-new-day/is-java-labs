package org.dealership.application.port.in.customorder;

import org.dealership.application.port.in.common.dto.ConfigurationDto;

import java.util.UUID;

public interface CreateCustomOrderUseCase {
    Response execute(Request request);

    record Request(
            UUID clientId,
            ConfigurationDto configuration
    ) {}

    record Response(UUID id) {}
}
