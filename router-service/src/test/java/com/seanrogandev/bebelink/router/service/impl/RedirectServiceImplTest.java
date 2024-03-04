package com.seanrogandev.bebelink.router.service.impl;

import com.seanrogandev.bebelink.router.client.GeneratorServiceWebClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class RedirectServiceImplTest {

    private RedirectServiceImpl redirectService;
    private GeneratorServiceWebClient mockWebClient;
    //util method
   // public static class Base64Util {
        public static boolean isValidBase64(String value) {
            try {
                Base64.getDecoder().decode(value);
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
    //}
    @BeforeEach
    void setUp() {
        mockWebClient = Mockito.mock(GeneratorServiceWebClient.class);
        redirectService = new RedirectServiceImpl(mockWebClient);
    }

    @Test
    void redirectFailNonBase64() {
        when(mockWebClient.getOrigin(anyString()))
                .thenAnswer((Answer<Mono<String>>) answer -> {
                    String arg = answer.getArgument(0);
                    if(!isValidBase64(arg)) {
                        return Mono.error(new IllegalArgumentException("URL string is not valid Base64"));
                    }
                    return Mono.just("Valid URL");
                });
        String invalidPath = "!@$%$#^#^&\"";
        StepVerifier.create(redirectService.redirect(invalidPath))
                .expectError(IllegalArgumentException.class)
                .verify();
    }
    @Test
    void redirectFailLengthExceeded() {
        StringBuilder sb = new StringBuilder();
        //build a string too long for the program
        sb.append("#".repeat(256));
        String path = sb.toString();
        when(mockWebClient.getOrigin(anyString()))
                .thenReturn(Mono.error(new IllegalArgumentException("Length of the string exceeds the maximum of 255 characters")));
        StepVerifier.create(redirectService.redirect(path))
                .expectError(IllegalArgumentException.class)
                .verify();
    }
    @Test
    void redirectSuccess() {
        String expectedUrl = "http://example.com/long-form-url";
        String shortUrlPath = "shortUrl";
        //stubbing the expected args and result
        when(mockWebClient.getOrigin(shortUrlPath))
                .thenReturn(Mono.just(expectedUrl));

        StepVerifier.create(redirectService.redirect(shortUrlPath))
                .expectNext(expectedUrl)
                .verifyComplete();
    }

}