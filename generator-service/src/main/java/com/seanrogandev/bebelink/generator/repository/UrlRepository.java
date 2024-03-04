package com.seanrogandev.bebelink.generator.repository;

import com.seanrogandev.bebelink.generator.model.entity.ShortUrl;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UrlRepository extends ReactiveCrudRepository<ShortUrl, UUID> {
    /**
     * Retrieves a Mono containing the ShortUrl object if it exists, using the origin(long-form) URL as an argument
     *
     * @param origin The long-form URL used to look up the shortened URL
     * @return a Mono containing the ShortUrl Object if it exists
     */
    Mono<ShortUrl> findByOrigin(String origin);

    /**
     * Retrieves a Mono containing the long-form origin URL string if it exists,
     * using the shortUrl(short-form) as an argument
     *
     * @param shortUrl the short-form url string used to look up the shortened URL object
     * @return a Mono containing the shortened URL if it exists
     */
    Mono<String> findOriginByShortUrl(String shortUrl);
}
