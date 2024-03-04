package com.seanrogandev.bebelink.generator.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@Builder
@Data

/**
 * A DTO Class representing a response to a request for a shortened URL.
 * Contains the shortened URL and the expiration date of the url
 */
public class GenerationResponse {
    private String url;
    private LocalDateTime expiration;
    public GenerationResponse() {}
    public GenerationResponse(String url, LocalDateTime expiration) {
        this.url = url;
        this.expiration = expiration;
    }

}
