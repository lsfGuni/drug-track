package com.example.drugtrack.security.controller;

import com.example.drugtrack.security.dto.UserListDTO;
import com.example.drugtrack.security.service.UserListService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
public class AdminController {

    private final UserListService userListService;

    public AdminController(UserListService userListService) {
        this.userListService = userListService;
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


}

