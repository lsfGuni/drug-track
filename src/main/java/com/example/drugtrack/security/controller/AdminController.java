package com.example.drugtrack.security.controller;

import com.example.drugtrack.security.dto.UpdateUserDTO;
import com.example.drugtrack.security.dto.UserDetailDTO;
import com.example.drugtrack.security.dto.UserListDTO;
import com.example.drugtrack.security.entity.User;
import com.example.drugtrack.security.service.UpdateUserService;
import com.example.drugtrack.security.service.UserListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
/**
 * AdminController는 관리자 기능을 담당하는 컨트롤러로,
 * 사용자 목록 조회, 사용자 정보 조회, 사용자 정보 업데이트,
 * 특정 조건에 따른 사용자 검색 기능을 제공합니다.
 */
@Controller
@RequestMapping
public class AdminController {

    private final UserListService userListService;
    private final UpdateUserService updateUserService;

    /**
     * AdminController의 생성자. UserListService와 UpdateUserService를 주입받아 초기화합니다.
     *
     * @param userListService 사용자 목록 서비스를 제공하는 서비스 클래스
     * @param updateUserService 사용자 업데이트 서비스를 제공하는 서비스 클래스
     */
    public AdminController(UserListService userListService,  UpdateUserService updateUserService) {
        this.userListService = userListService;
        this.updateUserService = updateUserService;
    }

    /**
     * 모든 사용자 목록을 조회하는 API 엔드포인트.
     *
     * @return 사용자 목록과 함께 응답이 담긴 Map 객체
     */
    @GetMapping("/user/get-user-list")
    @ResponseBody
    public Map<String, Object> getUserList() {
        List<UserListDTO> userDTOs = userListService.getAllUsers();
        Map<String, Object> response = new HashMap<>();
        response.put("response", userDTOs);
        return response;
    }

    /**
     * 특정 사용자의 상세 정보를 조회하는 API 엔드포인트.
     *
     * @param seq 사용자의 고유 식별자 (seq)
     * @return 사용자 정보가 포함된 ResponseEntity 객체
     */
    @GetMapping("user/details/{seq}")
    public ResponseEntity<UserDetailDTO> getUserDetails(@PathVariable Long seq) {
        System.out.println("Entering getUserDetails with seq: " + seq); // 메서드 진입 로그

        Optional<User> userOpt = userListService.getUserBySeq(seq);
        return userOpt.map(user -> {
            System.out.println("User found: " + user.getId()); // 사용자 정보가 조회되었을 때의 로그

            UserDetailDTO userDetailDTO = new UserDetailDTO(
                    user.getSeq(),
                    user.getId(),
                    user.getRole(),
                    user.getCompanyType(),
                    user.getCompanyName(),
                    user.getCompanyRegNumber(),
                    user.getPhoneNumber(),
                    user.getEmail(),
                    user.getActive(),
                    user.getUsername()
            );
            return ResponseEntity.ok(userDetailDTO);
        }).orElseGet(() -> {
            System.out.println("User not found for seq: " + seq); // 사용자 정보가 없을 때의 로그
            return ResponseEntity.notFound().build();
        });
    }

    /**
     * 사용자의 정보를 업데이트하는 API 엔드포인트.
     *
     * @param seq 업데이트할 사용자의 고유 식별자
     * @param updateUserDTO 업데이트할 사용자 정보가 담긴 DTO
     * @return 업데이트된 사용자 정보가 포함된 ResponseEntity 객체
     */
    @PutMapping("/user/update/{seq}")
    public ResponseEntity<User> updateUser(@PathVariable Long seq, @RequestBody UpdateUserDTO updateUserDTO) {
        User updatedUser = updateUserService.updateUser(seq, updateUserDTO);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * 검색 조건을 바탕으로 사용자를 검색하는 API 엔드포인트.
     *
     * @param companyRegNumber 사업자 등록 번호
     * @param id 사용자 아이디
     * @param companyName 회사명
     * @param startDate 검색 범위 시작 날짜
     * @param endDate 검색 범위 종료 날짜
     * @param companyType 회사 유형
     * @param phoneNumber 사용자 전화번호
     * @return 검색된 사용자 목록 또는 사용자 없음에 대한 응답 메시지
     */
    @GetMapping("/user/search")
    public ResponseEntity<?> searchByCriteria(
            @RequestParam(required = false) String companyRegNumber,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String companyType,
            @RequestParam(required = false) String phoneNumber) {

        List<User> users = userListService.searchUsers(companyRegNumber, id, companyName, startDate, endDate, companyType, phoneNumber);

        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "사용자를 찾을 수 없습니다."));
        }

        return ResponseEntity.ok(users);
    }


}

