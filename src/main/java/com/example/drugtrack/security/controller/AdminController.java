package com.example.drugtrack.security.controller;

import com.example.drugtrack.security.dto.UpdateUserDTO;
import com.example.drugtrack.security.dto.UserDetailDTO;
import com.example.drugtrack.security.dto.UserListDTO;
import com.example.drugtrack.security.entity.User;
import com.example.drugtrack.security.service.UpdateUserService;
import com.example.drugtrack.security.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping
public class AdminController {

    private final UserListService userListService;
    private final UpdateUserService updateUserService;

    @Autowired
    public AdminController(UserListService userListService,  UpdateUserService updateUserService) {
        this.userListService = userListService;
        this.updateUserService = updateUserService;
    }


    @GetMapping("/user/get-user-list")
    @ResponseBody
    public Map<String, Object> getUserList() {
        List<UserListDTO> userDTOs = userListService.getAllUsers();
        Map<String, Object> response = new HashMap<>();
        response.put("response", userDTOs);
        return response;
    }

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


    @PutMapping("/user/update/{seq}")
    public ResponseEntity<User> updateUser(@PathVariable Long seq, @RequestBody UpdateUserDTO updateUserDTO) {
        User updatedUser = updateUserService.updateUser(seq, updateUserDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/user/search")
    public ResponseEntity<?> searchByCriteria(
            @RequestParam(required = false) String companyRegNumber,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String companyType,
            @RequestParam(required = false) String phoneNumber) {

        System.out.println("Search request received");

        List<User> users = userListService.searchUsers(companyRegNumber, id, companyName, startDate, endDate, companyType, phoneNumber);

        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "사용자를 찾을 수 없습니다."));
        }

        return ResponseEntity.ok(users);
    }


}

