package org.example.dealership.domain.model.order.state;

public enum CustomCarOrderStatus {
    PLACED,
    APPROVED_BY_WAREHOUSE,
    WAITING_FOR_PAYMENT,
    PAID,
    WAITING_FOR_DELIVERY,
    READY_FOR_PICKUP,
    COMPLETED,
    CANCELED,
    ;

    public boolean isTerminal() {
        return this == COMPLETED || this == CANCELED;
    }

    public boolean canTransitionTo(CustomCarOrderStatus next) {
        if (next == null || this == next) {
            return false;
        }
        return switch (this) {
            case PLACED -> next == APPROVED_BY_WAREHOUSE || next == CANCELED;
            case APPROVED_BY_WAREHOUSE -> next == WAITING_FOR_PAYMENT || next == CANCELED;
            case WAITING_FOR_PAYMENT -> next == PAID || next == CANCELED;
            case PAID -> next == WAITING_FOR_DELIVERY;
            case WAITING_FOR_DELIVERY -> next == READY_FOR_PICKUP;
            case READY_FOR_PICKUP -> next == COMPLETED;
            case COMPLETED, CANCELED -> false;
        };
    }
}
