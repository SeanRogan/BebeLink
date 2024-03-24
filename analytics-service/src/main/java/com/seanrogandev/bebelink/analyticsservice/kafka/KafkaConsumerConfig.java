package com.seanrogandev.bebelink.analyticsservice.kafka;

import com.seanrogandev.bebelink.analyticsservice.model.RoutingEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
/**
 * Configuration class for Kafka consumer settings.
 * <p>
 * This class sets up the consumer configuration for consuming messages from Kafka topics.
 * It includes the creation of ReceiverOptions and ReactiveKafkaConsumerTemplate beans for consuming
 * RoutingEvent messages from a specified topic.
 */
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ReceiverOptions<String, RoutingEvent> routingEventReceiverOptions(@Value(value = "${spring.kafka.topics.routing-event-topic}") String topic, KafkaProperties props) {
        ReceiverOptions<String, RoutingEvent> basicReceiverOptions = ReceiverOptions.create(props.buildConsumerProperties());
        return basicReceiverOptions.subscription(Collections.singletonList(topic));
    }
    @Bean
    public ReactiveKafkaConsumerTemplate<String, RoutingEvent> routingEventKafkaConsumerTemplate(ReceiverOptions<String, RoutingEvent> receiverOptions) {
        return new ReactiveKafkaConsumerTemplate<>(receiverOptions);
    }
}
