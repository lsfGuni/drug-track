package com.example.drugtrack.security.service;

import com.example.drugtrack.security.dto.UserListDTO;
import com.example.drugtrack.security.entity.User;
import com.example.drugtrack.security.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * UserListService 클래스는 사용자 리스트를 조회하고, 페이징 기능을 제공하는 서비스입니다.
 * 데이터베이스에서 사용자 정보를 조회하고, 이를 DTO로 변환하여 반환합니다.
 */
@Service
public class UserListService {

    @PersistenceContext
    private EntityManager entityManager;

    private final UserRepository userRepository;   // 사용자 데이터 처리용 리포지토리

    /**
     * 생성자에서 UserRepository 주입.
     * @param userRepository 사용자 정보를 조회하는 리포지토리
     */
    public UserListService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * 모든 사용자 리스트를 조회하여 UserListDTO로 변환한 후 반환.
     * @return 모든 사용자 정보를 담은 리스트
     */
    public List<UserListDTO> getAllUsers() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

        // 모든 사용자 정보를 조회하고, DTO로 변환
        return userRepository.findAll().stream()
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
                        user.getActive(),
                        user.getRegDate() != null ? LocalDateTime.parse(user.getRegDate(), formatter) : null  // String을 LocalDateTime으로 변환
                ))
                .collect(Collectors.toList());
    }

    /**
     * 페이징 처리를 통해 사용자 리스트를 조회하는 메서드.
     * @param pageable 페이징 정보를 담은 객체
     * @return 페이징 처리된 사용자 정보를 담은 Page 객체
     */
    public Page<UserListDTO> getPaginatedUsers(Pageable pageable) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 사용자 정보를 페이징 처리하여 반환, 각 사용자 정보를 UserListDTO로 변환
        return userRepository.findAll(pageable)
                .map(user -> {
                    try {
                        return new UserListDTO(
                                user.getSeq(),
                                user.getId(),
                                user.getUsername(),
                                user.getRole(),
                                user.getCompanyType(),
                                user.getCompanyName(),
                                user.getCompanyRegNumber(),
                                user.getPhoneNumber(),
                                user.getEmail(),
                                user.getActive(),
                                user.getRegDate() != null ? LocalDateTime.parse(user.getRegDate(), formatter) : null
                        );
                    } catch (Exception e) {
                        // 매핑 오류 발생 시 오류 로그를 기록하고 null 반환
                        System.err.println("Error mapping user: " + e.getMessage());
                        return null;  // 매핑 오류 처리
                    }
                });
    }



    /**
     * 사용자 시퀀스 번호를 기반으로 상세 정보를 조회하는 메서드.
     * @param seq 사용자 시퀀스 번호
     * @return 해당 시퀀스 번호를 가진 사용자의 정보
     */
    public Optional<User> getUserBySeq(Long seq) {
        System.out.println("Entering getUserBySeq with seq: " + seq);
        return userRepository.findBySeq(seq); // 사용자 시퀀스 번호로 조회
    }


    /**
     * 페이징 처리를 통해 모든 사용자를 조회하는 메서드.
     * @param pageable 페이징 정보
     * @return 페이징 처리된 사용자 정보를 담은 Page 객체
     */
    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable); // 모든 사용자를 페이징하여 조회
    }

    /**
     * 다양한 조건을 기반으로 사용자를 검색하는 메서드.
     * @param companyRegNumber 사업자등록번호
     * @param id 사용자 ID
     * @param companyName 회사 이름
     * @param startDate 검색 시작 날짜
     * @param endDate 검색 종료 날짜
     * @param companyType 회사 타입
     * @param phoneNumber 전화번호
     * @return 조건에 맞는 사용자 리스트
     */
    public List<User> searchUsers(String companyRegNumber, String id, String companyName,
                                  String startDate, String endDate, String companyType, String phoneNumber) {
        // 전화번호에서 하이픈 제거
        String sanitizedPhoneNumber = null;
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            phoneNumber = phoneNumber.replaceAll("-", "");
        }

        // 조건에 맞는 사용자 조회
        return userRepository.findByAdvancedCriteria(
                companyRegNumber,
                id,
                companyName,
                companyType,
                phoneNumber,
                startDate,
                endDate);
    }


}
