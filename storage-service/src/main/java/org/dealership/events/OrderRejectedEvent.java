package org.dealership.events;

public record OrderRejectedEvent(
        String orderId,
        String traceId,
        String reason
) {
}
