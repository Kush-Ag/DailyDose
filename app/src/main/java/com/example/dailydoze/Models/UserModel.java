package com.example.dailydoze.Models;

import java.util.List;

public class UserModel {

    String name, username, password, pinCode;
    List<String> Allergies;

    public UserModel(String name, String username, String password, String pinCode, List<String> allergies) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.pinCode = pinCode;
        Allergies = allergies;
    }

    public UserModel() {

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
