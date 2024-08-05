package com.secret.platform.exception;

public class CustomerNotFoundException extends RuntimeException {

    // Constructor with message
    public CustomerNotFoundException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public CustomerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // Default constructor
    public CustomerNotFoundException() {
        super("Customer not found");
    }
}
