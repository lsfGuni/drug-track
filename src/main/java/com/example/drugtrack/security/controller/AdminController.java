package com.example.drugtrack.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
@ResponseBody
public class AdminController {

    @GetMapping("/dashboard")
    public String showAdminDashboard() {
        return "admin/dashboard";  // 관리자 대시보드로 이동
    }
}
