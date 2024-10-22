package com.example.drugtrack.security.service;

import com.example.drugtrack.security.dto.UpdateUserDTO;
import com.example.drugtrack.security.entity.User;
import com.example.drugtrack.security.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * UpdateUserService 클래스는 사용자 정보를 업데이트하는 서비스입니다.
 */
@Service
public class UpdateUserService {


    private final UserRepository userRepository;

    /**
     * UserRepository를 주입받아 UpdateUserService를 초기화합니다.
     *
     * @param userRepository 사용자 정보를 처리하기 위한 리포지토리
     */
    public UpdateUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 주어진 사용자 seq(고유 식별자)를 기준으로 사용자 정보를 업데이트합니다.
     *
     * @param seq             업데이트할 사용자의 고유 식별자
     * @param updateUserDTO    업데이트할 사용자 정보 DTO
     * @return 업데이트된 사용자 정보가 포함된 User 엔티티
     * @throws IllegalArgumentException 사용자 정보를 찾을 수 없는 경우 예외 발생
     */
    public User updateUser(Long seq, UpdateUserDTO updateUserDTO) {
        Optional<User> userOptional = userRepository.findById(seq);
        // Update the user's fields with the new data from UpdateUserDTO
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setCompanyType(updateUserDTO.getCompanyType());
            user.setCompanyName(updateUserDTO.getCompanyName());
            user.setCompanyRegNumber(updateUserDTO.getCompanyRegNumber());
            user.setPhoneNumber(updateUserDTO.getPhoneNumber());
            user.setEmail(updateUserDTO.getEmail());
            user.setActive(updateUserDTO.getActive());
            user.setUsername(updateUserDTO.getUsername());

            // Save the updated user entity
            return userRepository.save(user);
        } else {
            // Throw exception if the user is not found
            throw new IllegalArgumentException("User not found with seq: " + seq);
        }
    }
}
