package org.sergei.cargo.rest.exceptions;

/**
 * @author Sergei Visotsky
 */
public class FlightRuntimeException extends RuntimeException {

    public FlightRuntimeException() {
        super();
    }

    public FlightRuntimeException(String message) {
        super(message);
    }

    public FlightRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlightRuntimeException(Throwable cause) {
        super(cause);
    }

    protected FlightRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
