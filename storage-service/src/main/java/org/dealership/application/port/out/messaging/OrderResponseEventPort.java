package org.dealership.application.port.out.messaging;

import org.dealership.events.OrderApprovedEvent;
import org.dealership.events.OrderRejectedEvent;

public interface OrderResponseEventPort {
    void publishApproved(OrderApprovedEvent event);

    void publishRejected(OrderRejectedEvent event);
}
