package com.seanrogandev.bebelink.generator.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data

/**
 * A DTO Class representing a response to a request for a shortened URL.
 *  * Contains the shortened URL and the expiration date of the url
 */

public class GenerationResponse {
    private String url;
    private LocalDateTime expiration;

    public GenerationResponse() {
    }

    public GenerationResponse(String url, LocalDateTime expiration) {
        this.url = url;
        this.expiration = expiration;
    }

}
