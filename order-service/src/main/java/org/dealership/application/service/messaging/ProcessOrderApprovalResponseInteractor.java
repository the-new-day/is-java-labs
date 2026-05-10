package org.dealership.application.service.messaging;

import org.dealership.application.port.in.messaging.ProcessOrderApprovalResponseUseCase;
import org.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.dealership.application.port.out.persistence.StockCarOrderRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.OrderId;
import org.dealership.domain.model.order.CustomCarOrder;
import org.dealership.domain.model.order.StockCarOrder;
import org.dealership.domain.model.order.state.CustomCarOrderStatus;
import org.dealership.domain.model.order.state.StockCarOrderStatus;
import org.dealership.events.OrderApprovedEvent;
import org.dealership.events.OrderRejectedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

public class ProcessOrderApprovalResponseInteractor implements ProcessOrderApprovalResponseUseCase {

    private static final Logger log = LoggerFactory.getLogger(ProcessOrderApprovalResponseInteractor.class);

    private final StockCarOrderRepository stockOrderRepository;
    private final CustomCarOrderRepository customOrderRepository;

    public ProcessOrderApprovalResponseInteractor(
            StockCarOrderRepository stockOrderRepository,
            CustomCarOrderRepository customOrderRepository
    ) {
        this.stockOrderRepository = stockOrderRepository;
        this.customOrderRepository = customOrderRepository;
    }

    @Override
    public void onApproved(OrderApprovedEvent event) {
        log.info("Applying OrderApproved: orderId={}, traceId={}, assemblyOrderId={}",
                event.orderId(), event.traceId(), event.assemblyOrderId());
        applyTransition(event.orderId(), event.traceId(),
                StockCarOrderStatus.READY_FOR_PICKUP, CustomCarOrderStatus.WAITING_FOR_DELIVERY);
    }

    @Override
    public void onRejected(OrderRejectedEvent event) {
        log.info("Applying OrderRejected: orderId={}, traceId={}, reason={}",
                event.orderId(), event.traceId(), event.reason());
        applyTransition(event.orderId(), event.traceId(),
                StockCarOrderStatus.CANCELED, CustomCarOrderStatus.CANCELED);
    }

    private void applyTransition(
            String orderIdStr,
            String traceId,
            StockCarOrderStatus stockTarget,
            CustomCarOrderStatus customTarget
    ) {
        OrderId orderId = new OrderId(UUID.fromString(orderIdStr));
        Optional<StockCarOrder> stock = stockOrderRepository.findById(orderId);
        if (stock.isPresent()) {
            StockCarOrder order = stock.get();
            if (!order.getStatus().canTransitionTo(stockTarget)) {
                log.warn("Skipping invalid stock transition: orderId={}, traceId={}, from={}, to={}",
                        orderIdStr, traceId, order.getStatus(), stockTarget);
                return;
            }
            stockOrderRepository.save(order.withStatus(stockTarget));
            log.info("Stock order updated: orderId={}, traceId={}, status={}",
                    orderIdStr, traceId, stockTarget);
            return;
        }
        Optional<CustomCarOrder> custom = customOrderRepository.findById(orderId);
        if (custom.isPresent()) {
            CustomCarOrder order = custom.get();
            if (!order.getStatus().canTransitionTo(customTarget)) {
                log.warn("Skipping invalid custom transition: orderId={}, traceId={}, from={}, to={}",
                        orderIdStr, traceId, order.getStatus(), customTarget);
                return;
            }
            customOrderRepository.save(order.withStatus(customTarget));
            log.info("Custom order updated: orderId={}, traceId={}, status={}",
                    orderIdStr, traceId, customTarget);
            return;
        }
        throw new EntityNotFoundException("Order not found for response: " + orderIdStr);
    }
}
