package com.example.drugtrack.security.service;

import com.example.drugtrack.security.dto.UserListDTO;
import com.example.drugtrack.security.entity.User;
import com.example.drugtrack.security.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserListService {

    private final UserRepository userRepository;

    public UserListService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserListDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserListDTO(
                        user.getSeq(),
                        user.getId(),
                        user.getUsername(),
                        user.getRole(),
                        user.getCompanyType(),
                        user.getCompanyName(),
                        user.getCompanyRegNumber(),
                        user.getPhoneNumber(),
                        user.getEmail(),
                        user.getActive()
                ))
                .collect(Collectors.toList());
    }
}
