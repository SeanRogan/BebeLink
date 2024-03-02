package com.seanrogandev.bebelink.generator.service.impl;

import com.seanrogandev.bebelink.generator.dto.GenerationRequest;
import com.seanrogandev.bebelink.generator.dto.GenerationResponse;
import com.seanrogandev.bebelink.generator.entity.ShortUrl;
import com.seanrogandev.bebelink.generator.repository.UrlRepository;
import com.seanrogandev.bebelink.generator.service.GeneratorService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.function.Supplier;
@AllArgsConstructor

@Service
public class GeneratorServiceImpl implements GeneratorService {
    private final String BASE_62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final int len = 6;
    private final Supplier<Integer> randomIndexSupplier;
    private final UrlRepository repo;

    @Autowired
    public GeneratorServiceImpl(UrlRepository repo) {
        this.repo = repo;
        this.randomIndexSupplier = () -> (int)(Math.random() * BASE_62_CHARS.length());
    }

    /**
     * Creates a random base62 short code to replace a long-form URL
     *
     * @return a 7 digit base62 encoded string
     */
    public String generateShortUrl() {

        final StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i <= len; i++) {
            int index = randomIndexSupplier.get();
            char random = BASE_62_CHARS.charAt(index);
            sb.append(random);
        }
        return sb.toString();
    }

    /**
     * Builds a GenerationResponse object in response to requests for a shortened URL,
     * unless the URL already has been converted before, in which case it will
     * look up and return the shortened URL
     *
     * @param request the long-form url being shortened
     * @return a GenerationResponse object
     */
    @Override
    @Transactional
    public GenerationResponse generate(GenerationRequest request) {
        GenerationResponse response = new GenerationResponse();
        //check if the requested URL has already been shortened
        Optional<ShortUrl> existingUrl = repo.findByOrigin(request.getLongFormUrl());
        if(!existingUrl.isPresent()) {
            //if no existing URL is found matching the origin, generate a new code
            String shortUrl = generateShortUrl();
            //create an expiration date to assign to the new URL
            Date expirationPeriod = Date.from(LocalDateTime
                    .now()
                    .plusDays(7L)
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
            //build and save the shortUrl to the database
            repo.save(new ShortUrl().builder()
                    .url(shortUrl)
                    .active(true)
                    .expires(expirationPeriod)
                    .origin(request.getLongFormUrl())
                    .build());
            //return the shortUrl string and expiration Date in the response
            response.setUrl(shortUrl);
            response.setExpiration(expirationPeriod);
        } else {
            //if a code exists, look it up and return it with the response
            ShortUrl shortUrl = existingUrl.get();
            response.setUrl(shortUrl.getUrl());
            response.setExpiration(shortUrl.getExpires());
        }

        return response;
    }

    /**
     * provides the long-form URL origin string
     * associated with a shortened URL
     *
     * @param shortUrl the shortened URL
     * @return the long-form URL associated with the shortened URL
     */
    @Override
    public Optional<String> provideOrigin(String shortUrl) {
        Optional<ShortUrl> data = repo.findByUrl(shortUrl);
        if (data.isPresent()) {
            return Optional.of(data.get().getOrigin());
        }
        return Optional.empty();
    }
}
