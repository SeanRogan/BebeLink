package com.seanrogandev.bebelink.generator.service;

import com.seanrogandev.bebelink.generator.exception.URLGenerationException;
import com.seanrogandev.bebelink.generator.model.dto.GenerationRequest;
import com.seanrogandev.bebelink.generator.model.dto.GenerationResponse;
import com.seanrogandev.bebelink.generator.model.entity.ShortUrl;
import com.seanrogandev.bebelink.generator.repository.UrlRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.function.Supplier;

@AllArgsConstructor
@Service
@Slf4j
@Transactional
public class GeneratorService {
    private final String BASE_62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final int len = 6;
    private final Supplier<Integer> randomIndexSupplier;
    private final UrlRepository repo;
    private final DatabaseClient dbClient;

    @Autowired
    public GeneratorService(DatabaseClient dbClient, UrlRepository repo) {
        this.repo = repo;
        this.dbClient = dbClient;
        this.randomIndexSupplier = () -> (int) (Math.random() * BASE_62_CHARS.length());
    }
    public Date convertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Creates a random base62 short code to replace a long-form URL
     *
     * @return a 7 digit base62 encoded string
     */
    private Mono<String> generateShortUrl() {
        return Mono.fromSupplier(() -> {
            StringBuilder sb = new StringBuilder(len);
            for (int i = 0; i <= len; i++) {
                int index = randomIndexSupplier.get();
                char random = BASE_62_CHARS.charAt(index);
                sb.append(random);
            }
            return sb.toString();
        });
    }

    /**
     * Builds a GenerationResponse object in response to requests for a shortened URL,
     * unless the URL already has been converted before, in which case it will
     * look up and return the shortened URL
     *
     * @param request the long-form url being shortened
     * @return a GenerationResponse object
     */

    @Transactional
    public Mono<GenerationResponse> generate(GenerationRequest request) {
        log.info("Attempting to find or generate URL for: {}", request.getLongFormUrl());
        return repo.findByOrigin(request.getLongFormUrl())
                .flatMap(shortUrl -> {
                    log.info("Found existing URL: {}", shortUrl);
                    return Mono.just(new GenerationResponse(shortUrl.getShortUrl(), shortUrl.getExpires()));
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("No existing URL found, generating new URL.");
                    return generateShortUrl()
                            .flatMap(newUrl -> {
                                LocalDateTime expirationPeriod = LocalDateTime.now().plusDays(7L);
                                ShortUrl dbEntity = new ShortUrl()
                                        .setActive(true)
                                        .setExpires(expirationPeriod)
                                        .setShortUrl(newUrl)
                                        .setOrigin(request.getLongFormUrl());
                                log.info("Saving new URL entity: {}", dbEntity);

                                String sql = "INSERT INTO url (id, origin, short_url, created_on, last_updated, expires, active) VALUES (:id, :origin, :short_url, :created_on, :last_updated, :expires, :active) RETURNING *";

                                return dbClient.sql(sql)
                                        .bind("id", UUID.randomUUID()) // Assuming id is generated here if null
                                        .bind("origin", dbEntity.getOrigin())
                                        .bind("short_url", dbEntity.getShortUrl())
                                        .bind("created_on", LocalDateTime.now()) // Assuming current timestamp for creation
                                        .bind("last_updated", LocalDateTime.now())// Assuming current timestamp for last update
                                        .bind("expires", dbEntity.getExpires())
                                        .bind("active", dbEntity.isActive())
                                        .fetch()
                                        .one()
                                        .doOnSuccess(saved -> log.info("Successfully saved URL: {}", saved))
                                        .onErrorMap(err -> new URLGenerationException("Error saving URL", err))
                                        .map(saved -> new GenerationResponse(dbEntity.getShortUrl(), dbEntity.getExpires()));
                            });
                }))
                .doOnError(e -> log.error("Error in URL generation process", e));
    }

    /**
     * provides the long-form URL origin string
     * associated with a shortened URL
     *
     * @param shortUrl the shortened URL
     * @return the long-form URL associated with the shortened URL
     */
    public Mono<String> provideOrigin(String shortUrl) {
        return repo.findOriginByShortUrl(shortUrl)
                .doOnSuccess(origin -> log.info("Origin found for short URL:" + shortUrl))
                .onErrorResume(e -> Mono.error(new NoSuchElementException("Error fetching origin URL")));

    }
}
