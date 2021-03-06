package org.evgndev.exception;

/**
 * Created by evgndev on 21.08.16.
 */
public class RestException extends Exception {
    public RestException() {
    }

    public RestException(String message) {
        super(message);
    }

    public RestException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestException(Throwable cause) {
        super(cause);
    }

    public RestException(String message,
                         Throwable cause,
                         boolean enableSuppression,
                         boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }
}
