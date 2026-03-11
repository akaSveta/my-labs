package com.goncharova.labs.exception;

public class RepositoryException extends RuntimeException {

    public static final int HTTP_CODE = 500;
    public static final int CODE = 5000;

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getHttpCode() {
        return HTTP_CODE;
    }

    public int getCode() {
        return CODE;
    }
}