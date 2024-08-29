package com.example.drugtrack.security.service;

import com.example.drugtrack.security.entity.User;
import com.example.drugtrack.security.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserListService {

    private final UserRepository userRepository;

    public UserListService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Map<String, Object> getUserList() {
        List<User> users = userRepository.findAll();
        Map<String, Object> data = new HashMap<>();
        data.put("response", users);
        return data;
    }
}