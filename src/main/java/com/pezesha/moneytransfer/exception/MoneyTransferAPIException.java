package com.pezesha.moneytransfer.exception;

import org.springframework.http.HttpStatus;

public class MoneyTransferAPIException extends RuntimeException {

    private final HttpStatus status;
    private final String message;

    public MoneyTransferAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public MoneyTransferAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
