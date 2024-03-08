package com.seanrogandev.bebelink.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@Slf4j
public class WebRequestEventFilter implements WebFilter {
    private final ReactiveKafkaProducerTemplate<String, String> template;
    private String topicName = "routing-event";

    @Autowired
    public WebRequestEventFilter(ReactiveKafkaProducerTemplate<String, String> template) {
        this.template = template;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Prepare the data to be sent, but don't send it yet.
        // Corrected to use SenderResult<Void> to match the return type of ReactiveKafkaProducerTemplate.send


        // Chain the Kafka send operation with the web filter chain.
        // Using then() to ignore the send result and continue with the filter chain
        // only after the send operation completes successfully.
        return template.send(topicName, extractData(exchange.getRequest()))
                .then(chain.filter(exchange)) // Continue with the chain after send completes
                .onErrorResume(e -> {
                    // Log or handle the error gracefully
                    log.error("Error sending message to Kafka", e);
                    return chain.filter(exchange); // Optionally continue the chain even if there's an error
                });
    }

    private String extractData(ServerHttpRequest request) {
        String requestPath = request.getPath().toString();
        String localAdd = request.getLocalAddress().toString();
        String remoteAdd = request.getRemoteAddress().toString();

    }

}
