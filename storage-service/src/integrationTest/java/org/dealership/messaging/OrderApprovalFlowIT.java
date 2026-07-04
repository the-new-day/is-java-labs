package org.dealership.messaging;

import org.dealership.AbstractIntegrationTest;
import org.dealership.events.OrderRejectedEvent;
import org.dealership.events.OrderSentForApprovalEvent;
import org.dealership.events.PartRequirement;
import org.dealership.events.Topics;
import org.dealership.infrastructure.persistence.jpa.entity.OutboxEventJpaEntity;
import org.dealership.infrastructure.persistence.jpa.repository.OutboxEventJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@Import(OrderApprovalFlowIT.TestKafkaConsumer.class)
class OrderApprovalFlowIT extends AbstractIntegrationTest {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private OutboxEventJpaRepository outboxRepository;

    @Autowired
    private TestKafkaConsumer testConsumer;

    @Test
    void rejectsOrderWhenCarModelMissingAndPublishesOutboxEvent() {
        String orderId = UUID.randomUUID().toString();
        String traceId = UUID.randomUUID().toString();
        UUID carModelId = UUID.randomUUID();

        OrderSentForApprovalEvent event = new OrderSentForApprovalEvent(
                orderId,
                traceId,
                Instant.now(),
                "CUSTOM",
                null,
                carModelId,
                List.of(new PartRequirement(UUID.randomUUID(), 1))
        );

        kafkaTemplate.send(Topics.ORDER_SENT_FOR_APPROVAL, orderId, event);

        await().atMost(ofSeconds(30)).untilAsserted(() -> {
            List<OutboxEventJpaEntity> rows = outboxRepository.findAll();
            assertThat(rows)
                    .anyMatch(r -> r.getEventType().equals("OrderRejected")
                            && r.getAggregateId().equals(orderId));
        });

        await().atMost(ofSeconds(30)).untilAsserted(() -> {
            assertThat(testConsumer.rejected)
                    .anyMatch(e -> e.orderId().equals(orderId) && e.traceId().equals(traceId));
        });

        OutboxEventJpaEntity row = outboxRepository.findAll().stream()
                .filter(r -> r.getEventType().equals("OrderRejected")
                        && r.getAggregateId().equals(orderId))
                .findFirst()
                .orElseThrow();
        assertThat(row.isSent()).isTrue();
    }

    @Component
    static class TestKafkaConsumer {
        final List<OrderRejectedEvent> rejected = new CopyOnWriteArrayList<>();

        @KafkaListener(topics = Topics.ORDER_REJECTED, groupId = "integration-test-listener")
        void onRejected(OrderRejectedEvent event) {
            rejected.add(event);
        }
    }
}
