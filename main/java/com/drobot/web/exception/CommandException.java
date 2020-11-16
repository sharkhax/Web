package com.drobot.web.exception;

/**
 * An exception that provides information on errors occurred while processing a command.
 *
 * @author Vladislav Drobot
 */
public class CommandException extends Exception {

    /**
     * Constructs a CommandException object.
     */
    public CommandException() {
        super();
    }

    /**
     * Constructs a CommandException object with a given message.
     *
     * @param message String object of the given message.
     */
    public CommandException(String message) {
        super(message);
    }

    /**
     * Constructs a CommandException object with given message and cause.
     *
     * @param message String object of the given message.
     * @param cause   Throwable object of the given cause.
     */
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a CommandException object with a given cause.
     *
     * @param cause Throwable object of the given cause.
     */
    public CommandException(Throwable cause) {
        super(cause);
    }
}
