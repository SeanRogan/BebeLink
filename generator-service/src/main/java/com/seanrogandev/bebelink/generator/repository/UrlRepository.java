package com.seanrogandev.bebelink.generator.repository;

import com.seanrogandev.bebelink.generator.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UrlRepository extends JpaRepository<ShortUrl, Long> {
    /**
     * Retrieves an Optional<> containing the shortened URL object if it exists, using the origin(long-form) URL as an argument
     * @param origin The long-form URL used to look-up the shortened URL
     * @return an Optional<> containing the shortened URL if it exists.
     */
    Optional<ShortUrl> findByOrigin(String origin);

    /**
     * Retrieves an Optional<> containing the shortened URL object if it exists, using the url(shortform) as an argument
     * @param shortUrl the shortform url used to look-up the shortened URL object
     * @return an Optional<> containing the shortened URL if it exists.
     */
    Optional<ShortUrl> findByUrl(String shortUrl);
}
