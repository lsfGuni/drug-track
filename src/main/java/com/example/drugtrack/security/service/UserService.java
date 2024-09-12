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

    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }


    // 로그인 시 활성화 상태 확인
    public User findByIdAndActive(String id) {
        return userRepository.findByIdAndActive(id, "Y").orElse(null);
    }


    //비밀번호 찾기
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
    //비밀번호찾기-비밀번호 자동생성
    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    //회원탈퇴
    public void deactivateUser(User user) {
        user.setActive("N");
        user.setId(user.getId() + "_deleted");
        userRepository.save(user);
    }

    //회원탈퇴시 칼럼값 변경
    public User findByCompanyRegNumberAndActive(String companyRegNumber, String active) {
        return userRepository.findByCompanyRegNumberAndActive(companyRegNumber, active).orElse(null);
    }




}
