package com.seanrogandev.bebelink.generator.controller;

import com.seanrogandev.bebelink.generator.model.dto.GenerationRequest;
import com.seanrogandev.bebelink.generator.model.dto.GenerationResponse;
import com.seanrogandev.bebelink.generator.service.GeneratorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@Slf4j

public class GeneratorController {

    private final GeneratorService generatorService;

    /**
     * Generates a new shortened URL
     *
     * @param generationRequest the long-form URL to be shortened
     * @return the generated URL and the URL expiration date
     */
    @PostMapping("/generate")
    public Mono<ResponseEntity<GenerationResponse>> generateNewUrl(@RequestBody @Valid GenerationRequest generationRequest) {
        log.info("/generate endpoint hit...");
        return generatorService.generate(generationRequest)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                .doOnSuccess(success -> log.info("generate service success "))
                .onErrorResume(e -> {
                    log.warn("GENERATOR SERVICE ERROR");
                    log.warn(e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                });
    }

    /**
     * Retrieves the long-form URL associated with a shortened URL
     * <p> This end point is called by the router service when a redirect is requested.
     * It invokes the generator service to find the long-form origin URL string associated with a shortened URL.
     *
     * @param shortUrl the shortened URL used for redirect
     * @return the long-form URL aka origin URL
     */

    @GetMapping("/origin/{shortUrl}")
    public Mono<ResponseEntity<String>> provideOrigin(@PathVariable String shortUrl) {
        log.info("Request to provide origin for short URL: {}", shortUrl);
        return generatorService.provideOrigin(shortUrl)
                .map(origin -> ResponseEntity.ok().body(origin))
                .doOnSuccess(success -> log.info("Origin provided for short URL: {}", shortUrl))
                .onErrorResume(e -> {
                    log.error("Error fetching origin for short URL: {}", shortUrl, e);
                    return Mono.just(ResponseEntity.notFound().build());
                });
    }

}
