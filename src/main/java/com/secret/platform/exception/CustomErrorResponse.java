package com.secret.platform.exception;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CustomErrorResponse {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
