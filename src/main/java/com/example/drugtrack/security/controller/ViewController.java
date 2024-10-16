package com.example.drugtrack.security.controller;

import com.example.drugtrack.security.dto.UserListDTO;
import com.example.drugtrack.security.service.UserListService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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


    @GetMapping("/view/main")
    public String showMainPage() {
        return "main"; // main.html 템플릿을 반환
    }

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/view/login"; // 기본 경로 접근 시 로그인 페이지로 리다이렉트
    }

    @GetMapping("/view/user-list")
    public String getUserList(
            @RequestParam(defaultValue = "0") int page,  // Default to first page (page index starts from 0)
            @RequestParam(defaultValue = "10") int size, // Default to page size of 10
            Model model) {

        // Use a pageable object to request a specific page and size
        Pageable pageable = PageRequest.of(page, size);

        // Fetch a paginated list of users
        Page<UserListDTO> userPage = userListService.getPaginatedUsers(pageable);

        // Add paginated list and pagination details to the model
        model.addAttribute("users", userPage.getContent());  // List of users for the current page
        model.addAttribute("currentPage", page);  // Current page number
        model.addAttribute("totalPages", userPage.getTotalPages());  // Total number of pages
        model.addAttribute("totalItems", userPage.getTotalElements());  // Total number of items

        return "userList";  // Return the Thymeleaf template
    }

}


