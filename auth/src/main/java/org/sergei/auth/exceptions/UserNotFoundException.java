package org.sergei.auth.exceptions;

/**
 * @author Sergei Visotsky
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
