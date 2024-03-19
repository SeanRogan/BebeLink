package com.seanrogandev.bebelink.analyticsservice.kafka;

import com.seanrogandev.bebelink.analyticsservice.model.RoutingEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;

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
