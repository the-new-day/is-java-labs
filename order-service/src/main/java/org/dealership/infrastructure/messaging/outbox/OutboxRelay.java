package org.dealership.infrastructure.messaging.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dealership.events.OrderSentForApprovalEvent;
import org.dealership.events.Topics;
import org.dealership.infrastructure.persistence.jpa.entity.OutboxEventJpaEntity;
import org.dealership.infrastructure.persistence.jpa.repository.OutboxEventJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class OutboxRelay {

    private static final Logger log = LoggerFactory.getLogger(OutboxRelay.class);
    private static final int BATCH_SIZE = 50;

    private final OutboxEventJpaRepository outboxRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public OutboxRelay(
            OutboxEventJpaRepository outboxRepository,
            KafkaTemplate<String, Object> kafkaTemplate,
            ObjectMapper objectMapper
    ) {
        this.outboxRepository = outboxRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void relay() {
        List<OutboxEventJpaEntity> batch =
                outboxRepository.findBySentFalseOrderByCreatedAtAsc(PageRequest.of(0, BATCH_SIZE));
        if (batch.isEmpty()) {
            return;
        }
        for (OutboxEventJpaEntity entity : batch) {
            try {
                String topic = resolveTopic(entity.getEventType());
                Object payload = deserializePayload(entity);
                String traceId = extractTraceId(payload);
                kafkaTemplate.send(topic, entity.getAggregateId(), payload).get();
                entity.markSent();
                outboxRepository.save(entity);
                log.info("Outbox relay: sent event id={}, orderId={}, traceId={}",
                        entity.getId(), entity.getAggregateId(), traceId);
            } catch (Exception e) {
                log.error("Outbox relay: failed to send event id={}, orderId={}",
                        entity.getId(), entity.getAggregateId(), e);
            }
        }
    }

    private String resolveTopic(String eventType) {
        return switch (eventType) {
            case "OrderSentForApproval" -> Topics.ORDER_SENT_FOR_APPROVAL;
            default -> throw new IllegalStateException("Unknown outbox eventType: " + eventType);
        };
    }

    private Object deserializePayload(OutboxEventJpaEntity entity) throws JsonProcessingException {
        return switch (entity.getEventType()) {
            case "OrderSentForApproval" ->
                    objectMapper.readValue(entity.getPayload(), OrderSentForApprovalEvent.class);
            default -> throw new IllegalStateException("Unknown outbox eventType: " + entity.getEventType());
        };
    }

    private String extractTraceId(Object payload) {
        if (payload instanceof OrderSentForApprovalEvent e) {
            return e.traceId();
        }
        return "unknown";
    }
}
