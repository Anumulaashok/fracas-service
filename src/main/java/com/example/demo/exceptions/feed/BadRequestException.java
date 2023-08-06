package com.example.demo.exceptions.feed;


/**
 * The type Bad request exception.
 */
public class BadRequestException extends RuntimeException {
    /**
     * Instantiates a new Bad request exception.
     */
    public BadRequestException() {
    }

    /**
     * Instantiates a new Bad request exception.
     *
     * @param cause the cause
     */
    public BadRequestException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Bad request exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public BadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Instantiates a new Bad request exception.
     *
     * @param message the message
     */
    public BadRequestException(String message) {
        super(message);
    }
}
