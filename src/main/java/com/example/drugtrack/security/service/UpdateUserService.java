package com.example.drugtrack.security.service;

import com.example.drugtrack.security.dto.UpdateUserDTO;
import com.example.drugtrack.security.entity.User;
import com.example.drugtrack.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateUserService {

    @Autowired
    private UserRepository userRepository;

    public User updateUser(Long seq, UpdateUserDTO updateUserDTO) {
        Optional<User> userOptional = userRepository.findById(seq);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setCompanyType(updateUserDTO.getCompanyType());
            user.setCompanyName(updateUserDTO.getCompanyName());
            user.setCompanyRegNumber(updateUserDTO.getCompanyRegNumber());
            user.setPhoneNumber(updateUserDTO.getPhoneNumber());
            user.setEmail(updateUserDTO.getEmail());
            user.setActive(updateUserDTO.getActive());
            user.setUsername(updateUserDTO.getUsername());
            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found with seq: " + seq);
        }
    }
}
