package org.dealership.events;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderSentForApprovalEvent(
        String orderId,
        String traceId,
        Instant timestamp,
        String orderType,
        UUID carId,
        UUID carModelId,
        List<PartRequirement> requiredParts
) {
}
