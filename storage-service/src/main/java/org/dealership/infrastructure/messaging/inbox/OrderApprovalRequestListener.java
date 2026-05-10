package org.dealership.infrastructure.messaging.inbox;

import org.dealership.application.port.in.messaging.ProcessOrderApprovalRequestUseCase;
import org.dealership.events.OrderSentForApprovalEvent;
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
public class OrderApprovalRequestListener {

    private static final Logger log = LoggerFactory.getLogger(OrderApprovalRequestListener.class);
    private static final String EVENT_TYPE = "OrderSentForApproval";

    private final InboxMessageJpaRepository inboxRepository;
    private final ProcessOrderApprovalRequestUseCase processUseCase;

    public OrderApprovalRequestListener(
            InboxMessageJpaRepository inboxRepository,
            ProcessOrderApprovalRequestUseCase processUseCase
    ) {
        this.inboxRepository = inboxRepository;
        this.processUseCase = processUseCase;
    }

    @KafkaListener(topics = Topics.ORDER_SENT_FOR_APPROVAL, groupId = "storage-service")
    @Transactional
    public void handle(OrderSentForApprovalEvent event) {
        log.info("Received OrderSentForApproval: orderId={}, traceId={}",
                event.orderId(), event.traceId());
        UUID messageId = UUID.fromString(event.traceId());
        if (inboxRepository.existsById(messageId)) {
            log.info("Duplicate message ignored: orderId={}, traceId={}",
                    event.orderId(), event.traceId());
            return;
        }
        inboxRepository.save(new InboxMessageJpaEntity(messageId, EVENT_TYPE, event.orderId()));
        processUseCase.execute(event);
    }
}
