package com.seanrogandev.bebelink.router.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
@FeignClient(name="generator-service")
public interface GeneratorServiceClient {
    /**
     * Retrieves the origin URL associated with the shortUrl
     * @param shortUrl the shortened URL from the redirect attempt
     * @return the origin URL, or long-form URL associated with the shortened URL
     */
    @GetMapping("/origin/{shortUrl}")
    Optional<String> getOrigin(@PathVariable("shortUrl") String shortUrl);
}
