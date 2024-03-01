package com.erich.dev.springbootapirestwebflux.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }
}
