package org.dealership.infrastructure.messaging.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dealership.application.port.out.messaging.OrderResponseEventPort;
import org.dealership.events.OrderApprovedEvent;
import org.dealership.events.OrderRejectedEvent;
import org.dealership.infrastructure.persistence.jpa.entity.OutboxEventJpaEntity;
import org.dealership.infrastructure.persistence.jpa.repository.OutboxEventJpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderResponseEventOutboxAdapter implements OrderResponseEventPort {

    private static final String AGGREGATE_TYPE = "ORDER";
    private static final String APPROVED_TYPE = "OrderApproved";
    private static final String REJECTED_TYPE = "OrderRejected";

    private final OutboxEventJpaRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public OrderResponseEventOutboxAdapter(
            OutboxEventJpaRepository outboxRepository,
            ObjectMapper objectMapper
    ) {
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publishApproved(OrderApprovedEvent event) {
        enqueue(APPROVED_TYPE, event.orderId(), event);
    }

    @Override
    public void publishRejected(OrderRejectedEvent event) {
        enqueue(REJECTED_TYPE, event.orderId(), event);
    }

    private void enqueue(String eventType, String aggregateId, Object payload) {
        try {
            String json = objectMapper.writeValueAsString(payload);
            OutboxEventJpaEntity entity = new OutboxEventJpaEntity(
                    UUID.randomUUID(),
                    AGGREGATE_TYPE,
                    aggregateId,
                    eventType,
                    json
            );
            outboxRepository.save(entity);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize " + eventType + " event", e);
        }
    }
}
