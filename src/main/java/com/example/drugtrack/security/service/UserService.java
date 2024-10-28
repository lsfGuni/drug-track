package com.example.drugtrack.security.service;

import com.example.drugtrack.security.entity.User;
import com.example.drugtrack.security.entity.UserInfoHistory;
import com.example.drugtrack.security.repository.UserInfoHistoryRepository;
import com.example.drugtrack.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
/**
 * UserService 클래스는 사용자 관리를 담당하는 서비스로, 사용자 등록, 정보 업데이트, 비밀번호 관리, 회원탈퇴 등을 처리합니다.
 */
@Service
public class UserService {

    private final UserRepository userRepository;  // 사용자 정보를 처리하는 리포지토리
    private final PasswordEncoder passwordEncoder;  // 비밀번호 암호화를 위한 PasswordEncoder
    private final UserInfoHistoryRepository userInfoHistoryRepository; // 사용자 정보 변경 이력을 저장하는 리포지토리

    /**
     * UserService 생성자.
     * @param userRepository 사용자 정보 리포지토리
     * @param passwordEncoder 비밀번호 암호화 인코더
     * @param userInfoHistoryRepository 사용자 정보 변경 이력 리포지토리
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserInfoHistoryRepository userInfoHistoryRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userInfoHistoryRepository = userInfoHistoryRepository;
    }

    /**
     * 새로운 사용자를 등록하고 비밀번호를 암호화하여 저장.
     * @param user 사용자 정보
     * @return 저장된 사용자 정보
     */
    public User registerUser(User user) {
        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword())); // 비밀번호 암호화
        return userRepository.save(user);
    }

    /**
     * ID를 통해 사용자 조회.
     * @param id 사용자 ID
     * @return 조회된 사용자 또는 null
     */
    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }


    /**
     * 활성화된 사용자만 조회 (로그인 시).
     * @param id 사용자 ID
     * @return 활성화된 사용자 또는 null
     */
    public User findByIdAndActive(String id) {
        return userRepository.findByIdAndActive(id, "Y").orElse(null);
    }


    /**
     * 사용자 정보 업데이트 (이메일, 전화번호 변경 시).
     * 변경된 정보는 이력으로 저장.
     * @param userId 사용자 ID
     * @param email 새 이메일
     * @param phoneNumber 새 전화번호
     * @return 업데이트 성공 여부
     */
    @Transactional
    public boolean updateUserInfo(String userId, String email, String phoneNumber) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // 이메일 변경 이력 저장
            if (!email.equals(user.getEmail())) {
                saveChangeHistory(user.getSeq(), "email", user.getEmail(), email);
                user.setEmail(email);
            }

            // 전화번호 변경 이력 저장
            if (!phoneNumber.equals(user.getPhoneNumber())) {
                saveChangeHistory(user.getSeq(), "phone_number", user.getPhoneNumber(), phoneNumber);
                user.setPhoneNumber(phoneNumber);
            }

            // 사용자 정보 저장
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 사용자 정보 변경 이력을 저장.
     * @param userSeq 사용자 시퀀스
     * @param fieldName 변경된 필드 이름
     * @param oldValue 이전 값
     * @param newValue 새 값
     */
    private void saveChangeHistory(Long userSeq, String fieldName, String oldValue, String newValue) {
        // 변경 횟수 조회
        Integer maxChangeCount = userInfoHistoryRepository.findMaxChangeCountByUserSeq(userSeq);
        int changeCount = (maxChangeCount == null) ? 1 : maxChangeCount + 1;

        UserInfoHistory history = new UserInfoHistory();
        history.setUserSeq(userSeq);
        history.setChangedField(fieldName);
        history.setOldValue(oldValue);
        history.setNewValue(newValue);
        history.setChangeCount(changeCount);
        history.setChangeDate(new Date()); // 변경된 시간 기록

        userInfoHistoryRepository.save(history); // 변경 이력 저장
    }

    /**
     * 전화번호로 사용자 조회 (중복 검사).
     * @param phoneNumber 전화번호
     * @return 조회된 사용자 또는 null
     */
    public User findByPhoneNumber(String phoneNumber) { return userRepository.findByPhoneNumber(phoneNumber).orElse(null); }

    /**
     * 이메일로 사용자 조회 (비밀번호 찾기).
     * @param email 이메일
     * @return 조회된 사용자 또는 null
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    /**
     * 비밀번호 업데이트 (암호화 후 저장).
     * @param user 사용자
     * @param newPassword 새 비밀번호
     */
    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));  // 새 비밀번호 암호화
        userRepository.save(user);
    }

    /**
     * 사용자를 비활성화 (회원 탈퇴 처리).
     * @param user 탈퇴할 사용자
     */
    public void deactivateUser(User user) {
        user.setActive("N"); // 사용자 상태 비활성화
        user.setId(user.getId() + "_deleted");  // 사용자 ID에 "_deleted" 추가
        userRepository.save(user);
    }

    /**
     * 사업자등록번호와 활성화 상태로 사용자 조회.
     * @param companyRegNumber 사업자등록번호
     * @param active 활성화 상태
     * @return 조회된 사용자 또는 null
     */
    public User findByCompanyRegNumberAndActive(String companyRegNumber, String active) {
        return userRepository.findByCompanyRegNumberAndActive(companyRegNumber, active).orElse(null);
    }


    /**
     * 사용자 정보 업데이트 (비밀번호 변경 포함)
     * @param userId 사용자 ID
     * @param email 새 이메일
     * @param phoneNumber 새 전화번호
     * @param beforePassword 기존 비밀번호
     * @param afterPassword 변경할 비밀번호
     * @return 업데이트된 사용자 정보 (ID, 이메일, 전화번호, 새 비밀번호)
     */
    @Transactional
    public Map<String, String> updateUserInfom(String userId, String email, String phoneNumber, String beforePassword, String afterPassword) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Validate existing password
            if (!passwordEncoder.matches(beforePassword, user.getPassword())) {
                throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
            }

            // Update password if afterPassword is provided
            if (afterPassword != null && !afterPassword.isEmpty()) {
                user.setPassword(passwordEncoder.encode(afterPassword));
            }

            // Update email if provided
            if (email != null && !email.isEmpty() && !email.equals(user.getEmail())) {
                saveChangeHistory(user.getSeq(), "email", user.getEmail(), email);
                user.setEmail(email);
            }

            // Update phone number if provided
            if (phoneNumber != null && !phoneNumber.isEmpty() && !phoneNumber.equals(user.getPhoneNumber())) {
                saveChangeHistory(user.getSeq(), "phone_number", user.getPhoneNumber(), phoneNumber);
                user.setPhoneNumber(phoneNumber);
            }

            // Save updated user info
            userRepository.save(user);

            // Prepare the response with updated values
            Map<String, String> updatedUserInfo = new HashMap<>();
            updatedUserInfo.put("id", user.getId());
            updatedUserInfo.put("companyType", user.getCompanyType());
            updatedUserInfo.put("companyName", user.getCompanyName());
            updatedUserInfo.put("companyRegNumber", user.getCompanyRegNumber());
            updatedUserInfo.put("email", user.getEmail());
            updatedUserInfo.put("phoneNumber", user.getPhoneNumber());


            return updatedUserInfo;
        } else {
            throw new IllegalArgumentException("사용자 ID를 찾을 수 없습니다.");
        }
    }

}
