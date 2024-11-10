package com.example.dailydoze.Models;

public class SignUpResponse {
    String jwt, message;

    public SignUpResponse(String jwt, String message) {
        this.jwt = jwt;
        this.message = message;
    }

    public SignUpResponse() {

    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
