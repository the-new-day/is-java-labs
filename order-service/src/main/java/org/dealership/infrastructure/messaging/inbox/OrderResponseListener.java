package org.dealership.infrastructure.messaging.inbox;

import org.dealership.application.port.in.messaging.ProcessOrderApprovalResponseUseCase;
import org.dealership.events.OrderApprovedEvent;
import org.dealership.events.OrderRejectedEvent;
import org.dealership.events.Topics;
import org.dealership.infrastructure.persistence.jpa.entity.InboxMessageJpaEntity;
import org.dealership.infrastructure.persistence.jpa.repository.InboxMessageJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class OrderResponseListener {

    private static final Logger log = LoggerFactory.getLogger(OrderResponseListener.class);
    private static final String APPROVED_TYPE = "OrderApproved";
    private static final String REJECTED_TYPE = "OrderRejected";

    private final InboxMessageJpaRepository inboxRepository;
    private final ProcessOrderApprovalResponseUseCase responseUseCase;

    public OrderResponseListener(
            InboxMessageJpaRepository inboxRepository,
            ProcessOrderApprovalResponseUseCase responseUseCase
    ) {
        this.inboxRepository = inboxRepository;
        this.responseUseCase = responseUseCase;
    }

    @KafkaListener(topics = Topics.ORDER_APPROVED, groupId = "order-service")
    @Transactional
    public void handleApproved(OrderApprovedEvent event) {
        log.info("Received OrderApproved: orderId={}, traceId={}",
                event.orderId(), event.traceId());
        UUID messageId = UUID.fromString(event.traceId());
        if (inboxRepository.existsById(messageId)) {
            log.info("Duplicate OrderApproved ignored: orderId={}, traceId={}",
                    event.orderId(), event.traceId());
            return;
        }
        inboxRepository.save(new InboxMessageJpaEntity(messageId, APPROVED_TYPE, event.orderId()));
        responseUseCase.onApproved(event);
    }

    @KafkaListener(topics = Topics.ORDER_REJECTED, groupId = "order-service")
    @Transactional
    public void handleRejected(OrderRejectedEvent event) {
        log.info("Received OrderRejected: orderId={}, traceId={}",
                event.orderId(), event.traceId());
        UUID messageId = UUID.fromString(event.traceId());
        if (inboxRepository.existsById(messageId)) {
            log.info("Duplicate OrderRejected ignored: orderId={}, traceId={}",
                    event.orderId(), event.traceId());
            return;
        }
        inboxRepository.save(new InboxMessageJpaEntity(messageId, REJECTED_TYPE, event.orderId()));
        responseUseCase.onRejected(event);
    }
}
