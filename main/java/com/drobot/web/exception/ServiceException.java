package com.drobot.web.exception;

/**
 * An exception that provides information on errors thrown by Service objects.
 *
 * @author Vladislav Drobot
 */
public class ServiceException extends Exception {

    /**
     * Constructs a ServiceException object.
     */
    public ServiceException() {
        super();
    }

    /**
     * Constructs a ServiceException object with a given message.
     *
     * @param message String object of the given message.
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a ServiceException object with given message and cause.
     *
     * @param message String object of the given message.
     * @param cause   Throwable object of the given cause.
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a ServiceException object with a given cause.
     *
     * @param cause Throwable object of the given cause.
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
