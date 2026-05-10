package org.dealership.events;

import java.util.UUID;

public record OrderApprovedEvent(
        String orderId,
        String traceId,
        UUID assemblyOrderId
) {
}
