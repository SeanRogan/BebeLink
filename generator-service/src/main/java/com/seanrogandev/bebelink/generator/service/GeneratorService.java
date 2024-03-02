package com.seanrogandev.bebelink.generator.service;

import com.seanrogandev.bebelink.generator.dto.GenerationRequest;
import com.seanrogandev.bebelink.generator.dto.GenerationResponse;

import java.util.Optional;

public interface GeneratorService {
    GenerationResponse generate(GenerationRequest generationRequest);
    Optional<String> provideOrigin(String shortUrl);
}
