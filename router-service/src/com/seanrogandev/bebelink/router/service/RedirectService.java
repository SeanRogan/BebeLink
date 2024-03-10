package com.seanrogandev.bebelink.router.service;

import com.seanrogandev.bebelink.router.client.GeneratorServiceWebClient;
import com.seanrogandev.bebelink.router.exception.UrlNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Service implementation for URL redirection.
 *
 * This service provides functionality to resolve and redirect short URLs to their original long-form URLs.
 * It invokes the GeneratorServiceWebClient to communicate with the generator service to retrieve the original URL given a short URL path.
 */
@Service
@Slf4j
public class RedirectService {

    private GeneratorServiceWebClient generatorServiceClient;
    /**
     * Constructs a RedirectServiceImpl with a GeneratorServiceWebClient.
     *
     * @param webClient The web client used to communicate with the generator service.
     */
    @Autowired
    public RedirectService(GeneratorServiceWebClient webClient) {
        this.generatorServiceClient = webClient;
    }
    /**
     * Redirects to the original long-form URL based on the provided short URL path.
     *
     * This method asynchronously retrieves the original URL associated with the given short URL path.
     * It makes a call to the generator service using a Feign client to fetch the corresponding long-form URL.
     * If the original URL is successfully retrieved, it is returned as a String.
     *
     * @param path the path segment of the shortened URL, used to look up the original URL.
     * @return a CompletableFuture that, when completed, yields the original long-form URL as a String.
     *         The future completes exceptionally with an IllegalArgumentException if the original URL
     *         cannot be found for the given path, indicating that the short URL may not exist or may have expired.
     */

    public Mono<String> redirect(String path) {
        log.info("Retrieving long-form URL associated with tail: /" + path);
        return generatorServiceClient.getOrigin(path)
                .switchIfEmpty(Mono.error(new UrlNotFoundException("URL not found for: " + path)));
    }
}
