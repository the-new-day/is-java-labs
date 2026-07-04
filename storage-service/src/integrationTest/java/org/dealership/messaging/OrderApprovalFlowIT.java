package org.dealership.messaging;

import org.dealership.AbstractControllerIT;
import org.dealership.events.OrderSentForApprovalEvent;
import org.dealership.events.PartRequirement;
import org.dealership.events.Topics;
import org.dealership.infrastructure.persistence.jpa.entity.OutboxEventJpaEntity;
import org.dealership.infrastructure.persistence.jpa.repository.OutboxEventJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class OrderApprovalFlowIT extends AbstractControllerIT {

    static final UUID SEED_CAR_ID = UUID.fromString("00000000-0000-0000-0000-000000000501");
    static final UUID SEED_MODEL_ID = UUID.fromString("00000000-0000-0000-0000-000000000101");
    static final UUID SEED_PART_ID = UUID.fromString("00000000-0000-0000-0000-000000000205");

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private OutboxEventJpaRepository outboxRepository;

    @Autowired
    private TestKafkaConsumer testConsumer;

    @Test
    void rejectsCustomOrderWhenCarModelMissingAndPublishesOutboxEvent() {
        String orderId = UUID.randomUUID().toString();
        String traceId = UUID.randomUUID().toString();

        OrderSentForApprovalEvent event = new OrderSentForApprovalEvent(
                orderId,
                traceId,
                Instant.now(),
                "CUSTOM",
                null,
                UUID.randomUUID(),
                List.of(new PartRequirement(UUID.randomUUID(), 1))
        );

        kafkaTemplate.send(Topics.ORDER_SENT_FOR_APPROVAL, orderId, event);

        await().atMost(ofSeconds(30)).untilAsserted(() -> {
            List<OutboxEventJpaEntity> rows = outboxRepository.findAll();
            assertThat(rows)
                    .anyMatch(r -> r.getEventType().equals("OrderRejected")
                            && r.getAggregateId().equals(orderId));
        });

        await().atMost(ofSeconds(30)).untilAsserted(() ->
                assertThat(testConsumer.rejected)
                        .anyMatch(e -> e.orderId().equals(orderId) && e.traceId().equals(traceId))
        );

        OutboxEventJpaEntity row = outboxRepository.findAll().stream()
                .filter(r -> r.getEventType().equals("OrderRejected") && r.getAggregateId().equals(orderId))
                .findFirst().orElseThrow();
        assertThat(row.isSent()).isTrue();
    }

    @Test
    void approvesCustomOrderWhenCarModelExistsAndPublishesOutboxEvent() {
        String orderId = UUID.randomUUID().toString();
        String traceId = UUID.randomUUID().toString();

        OrderSentForApprovalEvent event = new OrderSentForApprovalEvent(
                orderId,
                traceId,
                Instant.now(),
                "CUSTOM",
                null,
                SEED_MODEL_ID,
                List.of(new PartRequirement(SEED_PART_ID, 2))
        );

        kafkaTemplate.send(Topics.ORDER_SENT_FOR_APPROVAL, orderId, event);

        await().atMost(ofSeconds(30)).untilAsserted(() -> {
            List<OutboxEventJpaEntity> rows = outboxRepository.findAll();
            assertThat(rows)
                    .anyMatch(r -> r.getEventType().equals("OrderApproved")
                            && r.getAggregateId().equals(orderId));
        });

        await().atMost(ofSeconds(30)).untilAsserted(() ->
                assertThat(testConsumer.approved)
                        .anyMatch(e -> e.orderId().equals(orderId) && e.traceId().equals(traceId))
        );

        OutboxEventJpaEntity row = outboxRepository.findAll().stream()
                .filter(r -> r.getEventType().equals("OrderApproved") && r.getAggregateId().equals(orderId))
                .findFirst().orElseThrow();
        assertThat(row.isSent()).isTrue();
    }

    @Test
    void approvesStockOrderWhenCarExistsAndPublishesOutboxEvent() {
        String orderId = UUID.randomUUID().toString();
        String traceId = UUID.randomUUID().toString();

        OrderSentForApprovalEvent event = new OrderSentForApprovalEvent(
                orderId,
                traceId,
                Instant.now(),
                "STOCK",
                SEED_CAR_ID,
                null,
                List.of()
        );

        kafkaTemplate.send(Topics.ORDER_SENT_FOR_APPROVAL, orderId, event);

        await().atMost(ofSeconds(30)).untilAsserted(() -> {
            List<OutboxEventJpaEntity> rows = outboxRepository.findAll();
            assertThat(rows)
                    .anyMatch(r -> r.getEventType().equals("OrderApproved")
                            && r.getAggregateId().equals(orderId));
        });

        await().atMost(ofSeconds(30)).untilAsserted(() ->
                assertThat(testConsumer.approved)
                        .anyMatch(e -> e.orderId().equals(orderId) && e.traceId().equals(traceId))
        );
    }

    @Test
    void rejectsStockOrderWhenCarMissing() {
        String orderId = UUID.randomUUID().toString();
        String traceId = UUID.randomUUID().toString();

        OrderSentForApprovalEvent event = new OrderSentForApprovalEvent(
                orderId,
                traceId,
                Instant.now(),
                "STOCK",
                UUID.randomUUID(),
                null,
                List.of()
        );

        kafkaTemplate.send(Topics.ORDER_SENT_FOR_APPROVAL, orderId, event);

        await().atMost(ofSeconds(30)).untilAsserted(() ->
                assertThat(testConsumer.rejected)
                        .anyMatch(e -> e.orderId().equals(orderId) && e.traceId().equals(traceId))
        );
    }
}
