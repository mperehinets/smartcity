package com.smartcity.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException {

    private String name;

    public InvalidJwtAuthenticationException(String message) {
        super(message);
    }

    public InvalidJwtAuthenticationException(String message, String name) {
        super(message);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
