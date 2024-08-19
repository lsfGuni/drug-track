package com.example.drugtrack.security.controller;

import com.example.drugtrack.security.dto.DeactivateUserRequest;
import com.example.drugtrack.security.dto.LoginRequest;
import com.example.drugtrack.security.dto.PasswordResetRequest;
import com.example.drugtrack.security.entity.User;
import com.example.drugtrack.security.jwt.JwtTokenProvider;
import com.example.drugtrack.security.service.EmailService;
import com.example.drugtrack.security.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Tag(name = "USER 관련 API", description = "USER정보를 관리하는 API")
@Controller
@RequestMapping("/user")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailService emailService;  // EmailService 주입받기 위한 필드 추가
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(summary = "회원 가입 post요청", description = "데이터베이스에 회원정보를 저장합니다.")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Username is already taken."));
        }
        userService.registerUser(user);
        return ResponseEntity.ok(Collections.singletonMap("result", "Y"));
    }

    @Operation(summary = "로그인 API", description = "DB에 저장된 회원정보를 이용하여 로그인합니다.")
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // 'active' 상태가 'Y'인 사용자만 찾도록 함
        User user = userService.findByCompanyRegNumberAndActive(loginRequest.getCompaneyRegNumber(), "Y");

        // 사용자가 null이거나, 사용자가 비활성화 상태인 경우 로그인 거부
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Invalid credentials or account is deactivated."));
        }

        // 정상적인 로그인 처리
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getCompaneyRegNumber(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);

        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);
        response.put("result", "Y");

        return ResponseEntity.ok(response);
    }


    @Operation(summary = "비밀번호 찾기", description = "계정 ID와 사업자구분값을 통해 비밀번호를 찾습니다.")
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody PasswordResetRequest request) {
        String companyRegNumber = request.getCompanyRegNumber();
        String companyType = request.getCompanyType();

        User user = userService.findByUsernameAndCompanyType(companyRegNumber, companyType);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("result", "N"));
        }

        // 임시 비밀번호 생성
        String tempPassword = generateTemporaryPassword();

        // 임시 비밀번호로 비밀번호 업데이트
        userService.updatePassword(user, tempPassword);

        // 이메일 발송 (EmailService가 있다고 가정)
        String message = "의약품 이력관리 API 임시 비밀번호입니다.: " + tempPassword;
        emailService.sendResetPasswordEmail(user.getEmail(), message);
        System.out.println(message);
        return ResponseEntity.ok(Collections.singletonMap("result", "Y"));
    }

    private String generateTemporaryPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder tempPassword = new StringBuilder();
        Random rnd = new Random();
        while (tempPassword.length() < 8) { // 비밀번호 길이 8자리
            int index = (int) (rnd.nextFloat() * chars.length());
            tempPassword.append(chars.charAt(index));
        }
        return tempPassword.toString();
    }

    @Operation(summary = "회원 탈퇴", description = "계정과 사업장 정보를 활용해 회원 탈퇴를 처리합니다.")
    @PostMapping("/deactivate")
    public ResponseEntity<?> deactivateUser(@RequestBody DeactivateUserRequest request) {
        String companyRegNumber = request.getCompanyRegNumber();
        String password = request.getPassword();

        // 거래처사업장등록번호로 사용자 찾기
        User user = userService.findByCompanyRegNumberAndActive(companyRegNumber, "Y");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("result", "N")); // 사용자를 찾을 수 없음
        }

        // 비밀번호 검증
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("result", "N")); // 비밀번호가 일치하지 않음
        }

        // 사용자 비활성화 (회원 탈퇴 처리)
        userService.deactivateUser(user);
        return ResponseEntity.ok(Collections.singletonMap("result", "Y")); // 탈퇴 성공
    }


}
