package com.seanrogandev.bebelink.users.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * ShortUrlDTO is a data transfer object
 * representing the relevant data contained
 * within a shortUrl database entity.
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ShortUrlDTO {

    private UUID id;

    private String origin;

    private String shortUrl;

    private LocalDateTime expires;

    private boolean active;

}
