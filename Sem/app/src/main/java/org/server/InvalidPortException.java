package org.server;

public class InvalidPortException extends IllegalArgumentException {
    public InvalidPortException(final String message) {
        super(message);
    }
}
