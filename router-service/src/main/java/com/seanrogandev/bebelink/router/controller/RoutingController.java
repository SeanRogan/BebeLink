package com.seanrogandev.bebelink.router.controller;

import com.seanrogandev.bebelink.router.service.RedirectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Controller for handling HTTP redirect requests.
 * <p>
 * This controller listens for incoming HTTP GET requests on dynamic paths. It utilizes the {@link RedirectService}
 * to determine the original URL associated with a shortened URL path and then redirects the client to that original URL.
 */
@AllArgsConstructor
@Controller
@Slf4j
public class RoutingController {

    private final RedirectService redirectService;

    /**
     * Handles the redirect from a shortened URL to its original long-form URL.
     * <p>
     * This method asynchronously fetches the original URL for a given short URL path. Upon successful retrieval,
     * it issues an HTTP 302 redirect response to the client, directing them to the original URL. If the original URL
     * cannot be found or another error occurs, it returns an HTTP 500 internal server error response.
     *
     * @param path The path segment of the shortened URL, used as a key to look up the original URL.
     * @return A {@link Mono} containing a {@link ResponseEntity} that either redirects the client to the
     * original URL or indicates an error with an appropriate HTTP status code.
     */
    @GetMapping("/{path}")
    public Mono<ResponseEntity<Object>> redirect(@PathVariable String path, ServerWebExchange exchange) {
        log.info("redirect requested for {domain}/" + path);
        //invoke redirect service,
        // map successful response to a 302 redirect response entity,
        // otherwise return a 500 Error code.
        return redirectService.redirect(path)
                //map the origin URL from the redirect method to the response entity with a temp-redirect status code of 302
                .map(origin -> ResponseEntity.status(302)
                        //add the "Location" header, for the redirect destination.
                        // when a browser receives a 302 or 303 code response with a "Location" header,
                        // it automatically redirects to the Location
                        .header("Location", origin)
                        .build())
                .onErrorResume(err -> Mono.just(ResponseEntity.internalServerError()
                            .body("Error during redirect" + err.getMessage())));

    }
}
