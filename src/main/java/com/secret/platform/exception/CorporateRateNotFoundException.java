package com.secret.platform.exception;

public class CorporateRateNotFoundException extends RuntimeException {
    public CorporateRateNotFoundException(String corpRateID) {
        super("Corporate Rate ID not found: " + corpRateID);
    }
}