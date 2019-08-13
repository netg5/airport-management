package org.sergei.processor.rest.exceptions;

/**
 * @author Sergei Visotsky
 */
public class FailedHttpRequestException extends RuntimeException {

    public FailedHttpRequestException() {
        super();
    }

    public FailedHttpRequestException(String message) {
        super(message);
    }

    public FailedHttpRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedHttpRequestException(Throwable cause) {
        super(cause);
    }

    protected FailedHttpRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
