package org.dealership.infrastructure.messaging.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dealership.application.port.out.messaging.OrderApprovalEventPort;
import org.dealership.events.OrderSentForApprovalEvent;
import org.dealership.infrastructure.persistence.jpa.entity.OutboxEventJpaEntity;
import org.dealership.infrastructure.persistence.jpa.repository.OutboxEventJpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderApprovalEventOutboxAdapter implements OrderApprovalEventPort {

    private static final String AGGREGATE_TYPE = "ORDER";
    private static final String EVENT_TYPE = "OrderSentForApproval";

    private final OutboxEventJpaRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public OrderApprovalEventOutboxAdapter(OutboxEventJpaRepository outboxRepository, ObjectMapper objectMapper) {
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publishSentForApproval(OrderSentForApprovalEvent event) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            OutboxEventJpaEntity entity = new OutboxEventJpaEntity(
                    UUID.randomUUID(),
                    AGGREGATE_TYPE,
                    event.orderId(),
                    EVENT_TYPE,
                    payload
            );
            outboxRepository.save(entity);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize OrderSentForApprovalEvent", e);
        }
    }
}
