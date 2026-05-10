package org.dealership.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.dealership.events.Topics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic orderSentForApprovalTopic() {
        return TopicBuilder.name(Topics.ORDER_SENT_FOR_APPROVAL).partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic orderApprovedTopic() {
        return TopicBuilder.name(Topics.ORDER_APPROVED).partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic orderRejectedTopic() {
        return TopicBuilder.name(Topics.ORDER_REJECTED).partitions(1).replicas(1).build();
    }
}
