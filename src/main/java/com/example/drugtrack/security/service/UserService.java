package com.example.drugtrack.security.service;

import com.example.drugtrack.security.entity.User;
import com.example.drugtrack.security.entity.UserInfoHistory;
import com.example.drugtrack.security.repository.UserInfoHistoryRepository;
import com.example.drugtrack.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoHistoryRepository userInfoHistoryRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserInfoHistoryRepository userInfoHistoryRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

        this.userInfoHistoryRepository = userInfoHistoryRepository;
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


    // 유저정보 변경 업데이트 메소드
    @Transactional
    public boolean updateUserInfo(String userId, String email, String phoneNumber) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Check if email has changed
            if (!email.equals(user.getEmail())) {
                saveChangeHistory(user.getSeq(), "email", user.getEmail(), email);
                user.setEmail(email);
            }

            // Check if phone number has changed
            if (!phoneNumber.equals(user.getPhoneNumber())) {
                saveChangeHistory(user.getSeq(), "phone_number", user.getPhoneNumber(), phoneNumber);
                user.setPhoneNumber(phoneNumber);
            }

            // Save updated user information
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }
    // 정보변경이력 메소드
    private void saveChangeHistory(Long userSeq, String fieldName, String oldValue, String newValue) {
        // Find the number of changes for this field
        Integer maxChangeCount = userInfoHistoryRepository.findMaxChangeCountByUserSeq(userSeq);
        int changeCount = (maxChangeCount == null) ? 1 : maxChangeCount + 1;

        UserInfoHistory history = new UserInfoHistory();
        history.setUserSeq(userSeq);
        history.setChangedField(fieldName);
        history.setOldValue(oldValue);
        history.setNewValue(newValue);
        history.setChangeCount(changeCount);
        history.setChangeDate(new Date());  // Current timestamp

        userInfoHistoryRepository.save(history);
    }



    //대표연락처 중복검사
    public User findByPhoneNumber(String phoneNumber) { return userRepository.findByPhoneNumber(phoneNumber).orElse(null); }

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
