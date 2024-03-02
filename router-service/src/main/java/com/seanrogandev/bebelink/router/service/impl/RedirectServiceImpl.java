package com.seanrogandev.bebelink.router.service.impl;

import com.seanrogandev.bebelink.router.client.GeneratorServiceClient;
import com.seanrogandev.bebelink.router.service.RedirectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Service
@Slf4j
public class RedirectServiceImpl implements RedirectService {
    private final GeneratorServiceClient generatorServiceClient;
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
    @Override
    public CompletableFuture<String> redirect(String path) {

        return CompletableFuture.supplyAsync(() -> {
            log.info("retrieving long-form URL associated with tail: /" + path);
            return generatorServiceClient.getOrigin(path)
                    .orElseThrow(() -> new IllegalArgumentException("URL not found for: " + path));
        });
    }
}
