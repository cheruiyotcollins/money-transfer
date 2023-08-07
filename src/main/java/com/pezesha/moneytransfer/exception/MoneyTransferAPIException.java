package com.pezesha.moneytransfer.exception;

import org.springframework.http.HttpStatus;

public class MoneyTransferAPIException extends RuntimeException {

    private HttpStatus status;
    private String message;

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
