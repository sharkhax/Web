package com.drobot.web.exception;

/**
 * An exception that provides information on errors thrown by a ConnectionPool.
 *
 * @author Vladislav Drobot
 */
public class ConnectionPoolException extends Exception {

    /**
     * Constructs a ConnectionPoolException object.
     */
    public ConnectionPoolException() {
        super();
    }

    /**
     * Constructs a ConnectionPoolException object with a given message.
     *
     * @param message String object of the given message.
     */
    public ConnectionPoolException(String message) {
        super(message);
    }

    /**
     * Constructs a ConnectionPoolException object with given message and cause.
     *
     * @param message String object of the given message.
     * @param cause   Throwable object of the given cause.
     */
    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a ConnectionPoolException object with a given cause.
     *
     * @param cause Throwable object of the given cause.
     */
    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }
}
