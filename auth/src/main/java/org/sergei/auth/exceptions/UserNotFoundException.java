package org.sergei.auth.exceptions;

/**
 * @author Sergei Visotsky
 */
public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 7327661642649545144L;

    public UserNotFoundException(String message) {
        super(message);
    }
}
