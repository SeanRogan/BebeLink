package com.seanrogandev.bebelink.router.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Base64;
@Component
public class GeneratorServiceWebClient {
    private final WebClient webClient;

    public GeneratorServiceWebClient(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    /**
     * Get the origin (long-form) URL from the generator service
     *
     * @param shortUrl the short form URL used to identify the origin
     * @return a {@link Mono} Mono containing the origin string or the appropriate Error message.
     */
    public Mono<String> getOrigin(String shortUrl) {
        try {
            // Decode string
            byte[] decoded = Base64.getDecoder().decode(shortUrl);
            // Encode it again and check if it matches the original string
            String encoded = Base64.getEncoder().encodeToString(decoded);
            assert shortUrl.equals(encoded);
        } catch (IllegalArgumentException e) {
            // If exception is caught, it's not a valid Base64
            return Mono.error(new IllegalArgumentException("URL string is not valid Base64"));
        }
        if (shortUrl.length() < 256) {
            return this.webClient.get()
                    .uri("http:/generator-service/origin/{shortUrl}", shortUrl)
                    .retrieve()
                    .bodyToMono(String.class)
                    .onErrorReturn("There was a problem retrieving the origin in the web client"); // Handle errors or provide fallbacks as needed
        } else
            return Mono.error(new IllegalArgumentException("Length of the string exceeds the maximum of 255 characters"));
    }

}