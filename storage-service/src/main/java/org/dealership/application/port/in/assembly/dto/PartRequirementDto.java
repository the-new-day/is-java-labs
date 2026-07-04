package org.dealership.application.port.in.assembly.dto;

import java.util.UUID;

public record PartRequirementDto(UUID partId, int quantity) {
}
