package com.seanrogandev.bebelink.apigateway.filter;

import com.seanrogandev.bebelink.apigateway.model.RoutingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
/**
 * Web filter for capturing web request events and publishing them to a Kafka topic.
 * <p>
 * This filter intercepts incoming HTTP requests and publishes their details as events to a Kafka topic
 * for further processing or analytics. The events are published asynchronously and the filter chain proceeds
 * with the request handling, ensuring minimal impact on the request processing latency.
 */
@Component
@Slf4j
public class WebRequestEventFilter implements WebFilter {
    private final ReactiveKafkaProducerTemplate<String, RoutingEvent > template;
    private String routingTopic = "routing-event";
    private String generationTopic = "generate-event";
    private String registrationTopic = "register-event";
    /**
     * Constructs a WebRequestEventFilter with a ReactiveKafkaProducerTemplate for event publication.
     *
     * @param template the ReactiveKafkaProducerTemplate to use for publishing events to Kafka
     */
    @Autowired
    public WebRequestEventFilter(ReactiveKafkaProducerTemplate<String, RoutingEvent> template) {
        this.template = template;
    }
    /**
     * Filters incoming web requests, capturing request details and publishing them as events to a Kafka topic.
     *
     * @param exchange the current server web exchange
     * @param chain provides a way to delegate to the next web filter in the chain
     * @return a Mono<Void> to indicate when request handling is complete
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String requestPath = exchange.getRequest().getPath().toString();

        // Using then() to ignore the send result and continue with the filter chain
        // only after the send operation completes successfully.
        return template.send(routingTopic, extractData(exchange.getRequest()))
                .log("request from: " + requestPath + "  : sent to routing topic - " + routingTopic)
                .then(chain.filter(exchange)) // Continue with the chain after send completes
                .onErrorResume(e -> {
                    // Log or handle the error gracefully
                    log.error("Error sending message to Kafka", e);
                    return chain.filter(exchange); // Optionally continue the chain even if there's an error
                });
    }
    /**
     * Extracts relevant details from the ServerHttpRequest and creates a RoutingEvent.
     *
     * @param request the ServerHttpRequest from which to extract event details
     * @return a new RoutingEvent populated with details from the request
     */
    private RoutingEvent extractData(ServerHttpRequest request) {
        return new RoutingEvent().builder()
                .requestPath(request.getPath().toString())
                .localAddress(request.getLocalAddress().toString())
                .remoteAddress(request.getRemoteAddress().toString())
                .build();

    }

}
