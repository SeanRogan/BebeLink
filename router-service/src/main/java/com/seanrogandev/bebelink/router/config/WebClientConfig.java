package com.seanrogandev.bebelink.router.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for WebClient.
 * <p>
 * This class is annotated with {@code @Configuration}, indicating that it is a source of bean definitions.
 * It defines beans related to the WebClient, which is part of Spring's Web Reactive framework for making non-blocking web requests.
 * </p>
 */
@Configuration
public class WebClientConfig {

    /**
     * Creates a {@link WebClient.Builder} bean with load-balanced capabilities.
     *
     * @return A load-balanced {@link WebClient.Builder} instance.
     */
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

}
