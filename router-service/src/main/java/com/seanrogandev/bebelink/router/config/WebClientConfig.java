package com.seanrogandev.bebelink.router.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

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
     * We configure the HttpClient to NOT follow redirects internally so that they are passed back to the client.
     *
     * @return A load-balanced {@link WebClient.Builder} instance.
     */
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        HttpClient httpClient = HttpClient.create().followRedirect(false);
        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient));
    }

}
