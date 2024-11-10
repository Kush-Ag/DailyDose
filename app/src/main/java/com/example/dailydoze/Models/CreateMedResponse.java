package com.example.dailydoze.Models;

public class CreateMedResponse {
    private String message;

    public CreateMedResponse(String message) {
        this.message = message;
    }

    public CreateMedResponse(){
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
