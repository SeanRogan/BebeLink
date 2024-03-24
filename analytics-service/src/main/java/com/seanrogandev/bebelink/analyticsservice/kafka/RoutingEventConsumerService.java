package com.seanrogandev.bebelink.analyticsservice.kafka;

import com.seanrogandev.bebelink.analyticsservice.model.RoutingEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
/**
 * Service for consuming RoutingEvent messages from a Kafka topic.
 * <p>
 * This service listens to a Kafka topic for RoutingEvent messages and processes them
 * as they arrive. It demonstrates how to set up a listener for Kafka messages using
 * ReactiveKafkaConsumerTemplate and process the messages reactively.
 */
@Service
@Slf4j
public class RoutingEventConsumerService implements CommandLineRunner {


    private ReactiveKafkaConsumerTemplate<String, RoutingEvent> template;

    @Autowired
    public RoutingEventConsumerService(ReactiveKafkaConsumerTemplate<String, RoutingEvent> template) {
        this.template = template;
    }
    /**
     * Consumes RoutingEvent messages from a specified Kafka topic.
     *
     * @return a Flux<RoutingEvent> that represents the stream of RoutingEvent messages consumed from Kafka
     */
    @KafkaListener(topics = "routing-event-topic", groupId = "analytics-group")
    private Flux<RoutingEvent> consumeRoutingEvent() {
        return template
                .receiveAutoAck()
//                .delayElements(Duration.ofSeconds(2L)) // BACKPRESSURE
                .doOnNext(consumerRecord -> log.info("received key={}, value={} from topic={}, offset={}\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n",
                        consumerRecord.key(),
                        consumerRecord.value(),
                        consumerRecord.topic(),
                        consumerRecord.offset())
                )
                .map(ConsumerRecord::value)
                .doOnNext(event -> log.info("successfully consumed {}={}\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n", RoutingEvent.class.getSimpleName(), event))
                .doOnError(throwable -> log.error("An error occurred while consuming : {}\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n", throwable.getMessage()));
    }

    /**
     * Starts the consumption of RoutingEvent messages upon application startup
     */
    @Override
    public void run(String... args) throws Exception {
        consumeRoutingEvent().subscribe();
    }
}
