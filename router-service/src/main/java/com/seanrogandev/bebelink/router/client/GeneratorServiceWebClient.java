package com.seanrogandev.bebelink.router.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Client for interacting with the Generator Service to retrieve original URLs from shortened versions.
 * <p>
 * This class encapsulates the logic for communicating with a service that provides the original (long-form) URL
 * corresponding to a given shortened URL. It leverages Spring's WebClient for non-blocking web requests and
 * reactive stream handling.
 */
@Component
@Slf4j
public class GeneratorServiceWebClient {
    private final WebClient webClient;

    /**
     * Constructs a GeneratorServiceWebClient with a configured WebClient.Builder.
     * <p>
     * The WebClient.Builder is expected to be pre-configured with necessary base URLs, codecs, and filters
     * appropriate for interacting with the generator service.
     *
     * @param builder A WebClient.Builder pre-configured for the generator service.
     */
    public GeneratorServiceWebClient(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    /**
     * Retrieves the original URL associated with a given shortened URL.
     * <p>
     * This method asynchronously fetches the original URL for a provided shortened URL. It performs a non-blocking
     * GET request to the generator service. If the shortened URL is valid and the original URL can be retrieved,
     * it is emitted as a {@link Mono<String>}. If the shortened URL is invalid, exceeds 255 characters in length,
     * or if an error occurs during the request, an appropriate error signal is emitted instead.
     *
     * @param shortUrl The shortened URL for which the original URL is to be retrieved.
     * @return A {@link Mono<String>} containing the original URL if successful, or emitting an error signal
     * if the URL is invalid, too long, or if an error occurs during the request.
     */
    public Mono<String> getOrigin(String shortUrl) {

        if (shortUrl.length() < 256) {
            return this.webClient.get()
                    .uri("http://generator-service/origin/{shortUrl}", shortUrl)
                    .retrieve()
                    .bodyToMono(String.class)
                    .onErrorReturn("There was a problem retrieving the origin in the web client"); // Handle errors or provide fallbacks as needed
        } else
            return Mono.error(new IllegalArgumentException("Length of the string exceeds the maximum of 255 characters"));
    }

}