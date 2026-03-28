package org.dealership.domain.model.order.state;

public enum StockCarOrderStatus {
    PLACED,
    APPROVED_BY_MANAGER,
    WAITING_FOR_PAYMENT,
    PAID,
    READY_FOR_PICKUP,
    COMPLETED,
    CANCELED;

    public boolean isTerminal() {
        return this == COMPLETED || this == CANCELED;
    }

    public boolean canTransitionTo(StockCarOrderStatus next) {
        if (next == null || this == next) {
            return false;
        }
        return switch (this) {
            case PLACED -> next == APPROVED_BY_MANAGER || next == CANCELED;
            case APPROVED_BY_MANAGER -> next == WAITING_FOR_PAYMENT || next == CANCELED;
            case WAITING_FOR_PAYMENT -> next == PAID || next == CANCELED;
            case PAID -> next == READY_FOR_PICKUP;
            case READY_FOR_PICKUP -> next == COMPLETED;
            case COMPLETED, CANCELED -> false;
        };
    }
}
