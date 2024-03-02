package com.seanrogandev.bebelink.router.service;

import java.util.concurrent.CompletableFuture;

public interface RedirectService {
    CompletableFuture<String> redirect(String path);
}
