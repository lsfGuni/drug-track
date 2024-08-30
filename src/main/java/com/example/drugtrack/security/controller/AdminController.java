package com.example.drugtrack.security.controller;

import com.example.drugtrack.security.dto.UpdateUserDTO;
import com.example.drugtrack.security.dto.UserListDTO;
import com.example.drugtrack.security.entity.User;
import com.example.drugtrack.security.service.UpdateUserService;
import com.example.drugtrack.security.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @GetMapping("/admin/dashboard")
    public String dashBoard() {
        return "admin/dashboard"; // resources/templates/admin/dashboard.html 파일로 이동
    }


    @GetMapping("/user/get-user-list")
    @ResponseBody
    public Map<String, Object> getUserList() {
        List<UserListDTO> userDTOs = userListService.getAllUsers();
        Map<String, Object> response = new HashMap<>();
        response.put("response", userDTOs);
        return response;
    }

    @GetMapping("/user/details/{seq}")
    public ResponseEntity<User> getUserDetails(@PathVariable Long seq) {
        Optional<User> user = userListService.getUserBySeq(seq);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/user/update/{seq}")
    public ResponseEntity<User> updateUser(@PathVariable Long seq, @RequestBody UpdateUserDTO updateUserDTO) {
        User updatedUser = updateUserService.updateUser(seq, updateUserDTO);
        return ResponseEntity.ok(updatedUser);
    }

}

