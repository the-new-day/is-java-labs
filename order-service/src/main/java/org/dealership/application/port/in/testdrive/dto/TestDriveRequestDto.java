package org.dealership.application.port.in.testdrive.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TestDriveRequestDto(
        UUID id,
        UUID clientId,
        UUID carId,
        LocalDateTime startsAt
) {}
