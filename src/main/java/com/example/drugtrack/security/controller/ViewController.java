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
/**
 * ViewController는 페이지 요청을 처리하는 컨트롤러입니다.
 * 사용자 로그인, 메인 페이지, 사용자 목록 페이지에 대한 GET 요청을 처리하며,
 * Thymeleaf 템플릿 엔진을 사용하여 UI를 렌더링합니다.
 */
@Controller
public class ViewController {


    private final UserListService userListService;
    /**
     * ViewController의 생성자. 사용자 목록을 처리하는 서비스를 주입받아 초기화합니다.
     *
     * @param userListService 사용자 목록 서비스
     */
    public ViewController(UserListService userListService) {
        this.userListService = userListService;
    }

    /**
     * 로그인 페이지를 렌더링하는 메서드.
     *
     * @return login.html 템플릿을 반환
     */
    @GetMapping("/view/login")
    public String showLoginPage() {
        return "login"; // login.html 템플릿을 반환
    }

    /**
     * 기본 경로 접근 시 로그인 페이지로 리다이렉트하는 메서드.
     *
     * @return /view/login으로 리다이렉트
     */
    @GetMapping("/view/main")
    public String showMainPage() {
        return "main"; // main.html 템플릿을 반환
    }

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/view/login"; // 기본 경로 접근 시 로그인 페이지로 리다이렉트
    }

    /**
     * 사용자 목록 페이지를 렌더링하는 메서드. 페이징을 처리하여 사용자 목록을 출력합니다.
     *
     * @param page  요청된 페이지 번호 (기본값은 0, 첫 번째 페이지)
     * @param size  페이지 크기 (기본값은 10)
     * @param model Thymeleaf 모델 객체로 데이터를 뷰에 전달
     * @return userList.html 템플릿을 반환
     */
    @GetMapping("/view/user-list")
    public String getUserList(
            @RequestParam(defaultValue = "0") int page,  // 기본 첫 번째 페이지
            @RequestParam(defaultValue = "10") int size, // 기본 페이지 크기 10
            Model model) {

        // 페이지 번호와 페이지 크기를 사용하여 Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, size);

        // 사용자 목록을 페이지 단위로 가져옴
        Page<UserListDTO> userPage = userListService.getPaginatedUsers(pageable);

        // 모델에 사용자 목록 및 페이지 정보를 추가
        model.addAttribute("users", userPage.getContent());   // 현재 페이지의 사용자 목록
        model.addAttribute("currentPage", page);  // 현재 페이지 번호
        model.addAttribute("totalPages", userPage.getTotalPages()); // 총 페이지 수
        model.addAttribute("totalItems", userPage.getTotalElements());  // 총 사용자 수

        return "userList";  // Thymeleaf 템플릿을 반환
    }

}


