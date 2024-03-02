package com.seanrogandev.bebelink.generator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Builder
@Data

/**
 * A DTO Class representing a response to a request for a shortened URL.
 * Contains the shortened URL and the expiration date of the url
 */
public class GenerationResponse {
    private String url;
    private Date expiration;
    public GenerationResponse() {}
    public GenerationResponse(String url, Date expiration) {
        this.url = url;
        this.expiration = expiration;
    }

}
