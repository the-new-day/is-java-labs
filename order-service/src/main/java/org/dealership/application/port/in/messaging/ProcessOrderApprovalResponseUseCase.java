package org.dealership.application.port.in.messaging;

import org.dealership.events.OrderApprovedEvent;
import org.dealership.events.OrderRejectedEvent;

public interface ProcessOrderApprovalResponseUseCase {
    void onApproved(OrderApprovedEvent event);

    void onRejected(OrderRejectedEvent event);
}
