package com.drobot.web.exception;

public class ConnectionPoolRuntimeException extends RuntimeException {
    public ConnectionPoolRuntimeException() {
        super();
    }

    public ConnectionPoolRuntimeException(String message) {
        super(message);
    }

    public ConnectionPoolRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolRuntimeException(Throwable cause) {
        super(cause);
    }
}
