package com.example.dailydoze.Models;

public class UpdateQtyResponse {

    String message;

    public UpdateQtyResponse(String message) {
        this.message = message;
    }

    public UpdateQtyResponse(){
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
