package com.seanrogandev.bebelink.generator.service;

import com.seanrogandev.bebelink.generator.model.dto.GenerationRequest;
import com.seanrogandev.bebelink.generator.model.dto.GenerationResponse;
import reactor.core.publisher.Mono;

public interface GeneratorService {
    Mono<GenerationResponse> generate(GenerationRequest generationRequest);

    Mono<String> provideOrigin(String shortUrl);
}
