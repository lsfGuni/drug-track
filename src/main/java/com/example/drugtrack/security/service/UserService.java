package com.example.drugtrack.security.service;

import com.example.drugtrack.security.entity.User;
import com.example.drugtrack.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    //비밀번호 찾기
    public User findByUsernameAndCompanyType(String username, String companyType) {
        return userRepository.findByUsernameAndCompanyType(username, companyType).orElse(null);
    }
    //비밀번호찾기-비밀번호 자동생성
    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void deactivateUser(User user) {
        user.setActive("N");
        userRepository.save(user);
    }

    public User findByCompanyRegNumberAndActive(String companyRegNumber, String active) {
        return userRepository.findByCompanyRegNumberAndActive(companyRegNumber, active).orElse(null);
    }

}
