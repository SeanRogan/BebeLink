package com.seanrogandev.bebelink.router.controller;

import com.seanrogandev.bebelink.router.service.RedirectService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.async.DeferredResult;
/**
 * Controller for handling HTTP redirect requests.
 * <p>
 * This controller listens for incoming HTTP GET requests on dynamic paths. It utilizes the {@link RedirectService}
 * to determine the original URL associated with a shortened URL path and then redirects the client to that original URL.
 */
@AllArgsConstructor
@Controller
public class RoutingController {

    private final RedirectService redirectService;

    /**
     * Handles the redirect from a shortened URL to its original long-form URL.
     * <p>
     * This method asynchronously fetches the original URL for a given short URL path. Upon successful retrieval,
     * it issues an HTTP 302 redirect response to the client, directing them to the original URL. If the original URL
     * cannot be found or another error occurs, it returns an HTTP 500 internal server error response.
     *
     * @param path    The path segment of the shortened URL, used as a key to look up the original URL.
     * @param request The incoming HTTP servlet request.
     * @return A {@link DeferredResult} containing a {@link ResponseEntity} that either redirects the client to the
     * original URL or indicates an error with an appropriate HTTP status code.
     */
    @GetMapping("/{path}")
    public DeferredResult<ResponseEntity<?>> redirect(@PathVariable String path, HttpServletRequest request) {
        DeferredResult<ResponseEntity<?>> result = new DeferredResult<>();

        redirectService.redirect(path).thenAccept(origin -> {
            result.setResult(ResponseEntity.status(HttpStatus.SC_MOVED_TEMPORARILY)
                    .header("Location", origin)
                    .build());
        }).exceptionally(ex -> {
            result.setErrorResult(
                    ResponseEntity.internalServerError().body("Error during redirect: " + ex.getMessage())
            );
            return null; // This is necessary to handle the exception and continue.
        });
        return result;
    }
}
