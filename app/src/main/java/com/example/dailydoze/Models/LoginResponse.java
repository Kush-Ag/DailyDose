package com.example.dailydoze.Models;

import java.util.List;

public class LoginResponse {
    String message,jwt,name,username,pinCode;
    List<String> Allergies;

    public LoginResponse(String message, String jwt, String name, String username, String pinCode, List<String> allergies) {
        this.message = message;
        this.jwt = jwt;
        this.name = name;
        this.username = username;
        this.pinCode = pinCode;
        Allergies = allergies;
    }

    public LoginResponse(){
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public List<String> getAllergies() {
        return Allergies;
    }

    public void setAllergies(List<String> allergies) {
        Allergies = allergies;
    }
}
