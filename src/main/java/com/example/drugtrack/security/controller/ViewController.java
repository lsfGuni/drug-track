package com.example.drugtrack.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

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

    @GetMapping("/main")
    public String showAuthenticatedMainPage() {
        return "main"; // 로그인 성공 시 main.html 템플릿을 반환
    }

}


