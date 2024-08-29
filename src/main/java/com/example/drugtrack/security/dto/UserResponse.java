package com.example.drugtrack.security.dto;

import com.example.drugtrack.security.entity.User;

import java.util.List;

public class UserResponse {
    private List<User> response;

    // Getters and Setters

    public List<User> getResponse() {
        return response;
    }

    public void setResponse(List<User> response) {
        this.response = response;
    }
}
