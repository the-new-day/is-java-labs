package org.dealership.messaging;

import org.dealership.events.OrderApprovedEvent;
import org.dealership.events.OrderRejectedEvent;
import org.dealership.events.Topics;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class TestKafkaConsumer {

    public final List<OrderRejectedEvent> rejected = new CopyOnWriteArrayList<>();
    public final List<OrderApprovedEvent> approved = new CopyOnWriteArrayList<>();

    @KafkaListener(topics = Topics.ORDER_REJECTED, groupId = "integration-test-listener-rejected")
    public void onRejected(OrderRejectedEvent event) {
        rejected.add(event);
    }

    @KafkaListener(topics = Topics.ORDER_APPROVED, groupId = "integration-test-listener-approved")
    public void onApproved(OrderApprovedEvent event) {
        approved.add(event);
    }
}
