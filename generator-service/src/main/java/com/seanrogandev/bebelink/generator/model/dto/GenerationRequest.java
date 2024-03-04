package com.seanrogandev.bebelink.generator.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A DTO Class representing the request for a shortened URL
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class GenerationRequest {
    String longFormUrl;
}
