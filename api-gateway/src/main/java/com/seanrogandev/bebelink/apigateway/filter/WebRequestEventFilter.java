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

@Component
@Slf4j
public class WebRequestEventFilter implements WebFilter {
    private final ReactiveKafkaProducerTemplate<String, RoutingEvent> template;
    private String routingTopic = "routing-event";
    private String generationTopic = "generate-event";
    private String registrationTopic = "register-event";

    @Autowired
    public WebRequestEventFilter(ReactiveKafkaProducerTemplate<String, RoutingEvent> template) {
        this.template = template;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String requestPath = exchange.getRequest().getPath().toString();

        // Using then() to ignore the send result and continue with the filter chain
        // only after the send operation completes successfully.
        return template.send(routingTopic, extractData(exchange.getRequest()))
                .log("request from: " + requestPath + "  :sent to routing topic.")
                .then(chain.filter(exchange)) // Continue with the chain after send completes
                .onErrorResume(e -> {
                    // Log or handle the error gracefully
                    log.error("Error sending message to Kafka", e);
                    return chain.filter(exchange); // Optionally continue the chain even if there's an error
                });
    }



    private RoutingEvent extractData(ServerHttpRequest request) {
        return new RoutingEvent().builder()
                .requestPath(request.getPath().toString())
                .localAddress(request.getLocalAddress().toString())
                .remoteAddress(request.getRemoteAddress().toString())
                .build();

    }

}
