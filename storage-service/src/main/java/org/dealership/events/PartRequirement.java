package org.dealership.events;

import java.util.UUID;

public record PartRequirement(UUID partId, int quantity) {
}
