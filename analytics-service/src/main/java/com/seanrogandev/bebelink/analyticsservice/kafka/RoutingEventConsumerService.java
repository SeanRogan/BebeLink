package com.seanrogandev.bebelink.analyticsservice.kafka;

import com.seanrogandev.bebelink.analyticsservice.model.RoutingEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class RoutingEventConsumerService implements CommandLineRunner {

    private ReactiveKafkaConsumerTemplate<String, RoutingEvent> template;

    @Autowired
    public RoutingEventConsumerService(ReactiveKafkaConsumerTemplate<String, RoutingEvent> template) {
        this.template = template;
    }

    private Flux<RoutingEvent> consumeRoutingEvent() {
        return template
                .receiveAutoAck()
                // .delayElements(Duration.ofSeconds(2L)) // BACKPRESSURE
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


    @Override
    public void run(String... args) throws Exception {
        consumeRoutingEvent().subscribe();
    }
}
