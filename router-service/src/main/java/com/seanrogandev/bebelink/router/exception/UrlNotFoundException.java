package com.seanrogandev.bebelink.router.exception;

/**
 * Exception thrown when a URL cannot be found.
 * <p>
 * This exception is used within the URL redirection service to indicate that
 * no corresponding long-form URL could be located for a given short URL. It may
 * be thrown during the process of attempting to retrieve the original URL from a
 * database or an external service if the short URL does not match any existing records.
 * </p>
 *
 * <p>
 * This class extends {@link RuntimeException}, allowing it to be used in scenarios
 * where checked exceptions are not desirable. By using this unchecked exception,
 * the API encourages callers to handle the potential absence of a URL explicitly,
 * either through catching the exception or by using alternative error handling
 * mechanisms provided by frameworks such as Spring WebFlux.
 * </p>
 */
public class UrlNotFoundException extends RuntimeException {

    /**
     * Constructs a new UrlNotFoundException with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public UrlNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new UrlNotFoundException with the specified detail message and cause.
     * <p>Note that the detail message associated with {@code cause} is not automatically incorporated
     * into this exception's detail message.</p>
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method). (A {@code null} value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     */
    public UrlNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
