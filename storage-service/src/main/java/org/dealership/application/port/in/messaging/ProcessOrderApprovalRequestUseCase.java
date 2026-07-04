package org.dealership.application.port.in.messaging;

import org.dealership.events.OrderSentForApprovalEvent;

public interface ProcessOrderApprovalRequestUseCase {
    void execute(OrderSentForApprovalEvent event);
}
