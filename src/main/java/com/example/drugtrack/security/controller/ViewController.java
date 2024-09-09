package com.example.drugtrack.security.controller;

import com.example.drugtrack.security.dto.UserListDTO;
import com.example.drugtrack.security.service.UserListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewController {


    private final UserListService userListService;

    public ViewController(UserListService userListService) {
        this.userListService = userListService;
    }


    @GetMapping("/view/login")
    public String showLoginPage() {
        return "login"; // login.html 템플릿을 반환
    }

    @GetMapping("/view/register")
    public String showRegisterPage() {
        return "register"; // register.html 템플릿을 반환
    }

    @GetMapping("/view/main")
    public String showMainPage() {
        return "main"; // main.html 템플릿을 반환
    }

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/view/login"; // 기본 경로 접근 시 로그인 페이지로 리다이렉트
    }

    @GetMapping("/view/user-list")
    public String getUserList(Model model) {
        List<UserListDTO> userDTOs = userListService.getAllUsers(); // Fetch all users
        model.addAttribute("users", userDTOs);  // Add the list of users to the model
        return "userList";  // Return Thymeleaf template for displaying users
    }



    @GetMapping("/view/user-detail")
    public String showUserDetailPage() {
        return "userDetail";  // userDetail.html 템플릿을 반환
    }
}


