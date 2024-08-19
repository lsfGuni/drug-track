package com.example.drugtrack.security.dto;

public class LoginRequest {


    private String password;
    private String companeyRegNumber;

    // Getters and Setters

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompaneyRegNumber() {
        return companeyRegNumber;
    }

    public void setCompaneyRegNumber(String companeyRegNumber) {
        this.companeyRegNumber = companeyRegNumber;
    }
}
