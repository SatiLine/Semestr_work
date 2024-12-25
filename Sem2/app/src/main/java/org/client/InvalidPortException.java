package org.client;

public class InvalidPortException extends IllegalArgumentException {
    public InvalidPortException(final String message) {
        super(message);
    }
}
