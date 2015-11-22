/*
 * Copyright (c) 2015. America Voice LLC.  All rights reserved.
 */
package gt.transparente.app.data.exception;

/**
 * Exception throw by the application when password validation can't return a valid result.
 */
public class NoMoreDataAvailableException extends Exception {

    public NoMoreDataAvailableException() {
        super();
    }

    public NoMoreDataAvailableException(final String message) {
        super(message);
    }

    public NoMoreDataAvailableException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NoMoreDataAvailableException(final Throwable cause) {
        super(cause);
    }
}
