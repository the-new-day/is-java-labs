package org.example.dealership.domain;

import org.example.dealership.domain.model.order.state.CustomCarOrderStatus;
import org.example.dealership.domain.model.order.state.StockCarOrderStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {
    @Test
    void shouldHandleStockOrderTransitions() {
        assertTrue(StockCarOrderStatus.PLACED.canTransitionTo(StockCarOrderStatus.APPROVED_BY_MANAGER));
        assertFalse(StockCarOrderStatus.PLACED.canTransitionTo(StockCarOrderStatus.PAID));
        assertTrue(StockCarOrderStatus.CANCELED.isTerminal());
        assertTrue(StockCarOrderStatus.COMPLETED.isTerminal());
    }

    @Test
    void shouldHandleCustomOrderTransitions() {
        assertTrue(CustomCarOrderStatus.PLACED.canTransitionTo(CustomCarOrderStatus.APPROVED_BY_WAREHOUSE));
        assertFalse(CustomCarOrderStatus.PLACED.canTransitionTo(CustomCarOrderStatus.PAID));
        assertTrue(CustomCarOrderStatus.CANCELED.isTerminal());
        assertTrue(CustomCarOrderStatus.COMPLETED.isTerminal());
    }
}
