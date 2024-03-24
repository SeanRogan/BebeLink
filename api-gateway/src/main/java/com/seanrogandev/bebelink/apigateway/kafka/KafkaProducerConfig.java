package com.seanrogandev.bebelink.apigateway.kafka;

import com.seanrogandev.bebelink.apigateway.model.RoutingEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.SenderOptions;

import java.util.HashMap;
import java.util.Map;
/**
 * Configuration class for Kafka producer settings.
 * <p>
 * This class configures a ReactiveKafkaProducerTemplate for sending messages to Kafka topics.
 * It also includes configuration for creating a Kafka topic if it doesn't already exist.
 */
@Configuration

public class KafkaProducerConfig {
    @Value("${spring.kafka.producer.acks}")
    private String acks;
    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;
    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;
    @Value("${spring.kafka.producer.properties.delivery.timeout.ms}")
    private Integer deliveryTimeout;
    @Value("${spring.kafka.producer.properties.linger.ms}")
    private Integer linger;
    @Value("${spring.kafka.producer.properties.request.timeout.ms}")
    private Integer requestTimeout;
    /**
     * Creates a ReactiveKafkaProducerTemplate bean for producing RoutingEvent messages.
     *
     * @return a configured instance of ReactiveKafkaProducerTemplate
     */
    @Bean
    public ReactiveKafkaProducerTemplate<String, RoutingEvent> kafkaProducerTemplate() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.ACKS_CONFIG, acks);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, deliveryTimeout);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeout);
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5);

        return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(props));
    }
    /**
     * Configures and creates a new topic for routing events if it doesn't already exist.
     *
     * @return a NewTopic instance representing the routing event topic
     */
    @Bean
    public NewTopic createRoutingTopic() {
        return (TopicBuilder.name("routing-event-topic")
                .partitions(3)
                .replicas(3)
                .configs(Map.of("min.insync.replicas", "2"))
                .build());
    }


}
