package com.seanrogandev.bebelink.router.service;
import reactor.core.publisher.Mono;


public interface RedirectService {
    Mono<String> redirect(String path);
}
