package org.dealership.application.port.out.messaging;

import org.dealership.events.OrderSentForApprovalEvent;

public interface OrderApprovalEventPort {
    void publishSentForApproval(OrderSentForApprovalEvent event);
}
