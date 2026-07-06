package com.bharath.loanapplication.model;

public class AuthResponse {

    private String message;

    public AuthResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}