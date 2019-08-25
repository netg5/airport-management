package org.sergei.booking.rest.exceptions;

/**
 * @author Sergei Visotsky
 */
public class FlightRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -6122185357930236352L;

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
