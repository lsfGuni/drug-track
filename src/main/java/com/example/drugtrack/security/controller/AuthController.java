package com.example.drugtrack.security.controller;

import com.example.drugtrack.security.dto.DeactivateUserRequest;
import com.example.drugtrack.security.dto.LoginRequest;
import com.example.drugtrack.security.dto.PasswordResetRequest;
import com.example.drugtrack.security.entity.User;
import com.example.drugtrack.security.jwt.JWTUtil;
import com.example.drugtrack.security.service.EmailService;
import com.example.drugtrack.security.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Tag(name = "USER 관련 API", description = "USER정보를 관리하는 API")
@Controller
@ResponseBody
@RequestMapping("/user")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final EmailService emailService;  // EmailService 주입받기 위한 필드 추가
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, EmailService emailService, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(summary = "회원 가입 post요청", description = "데이터베이스에 회원정보를 저장합니다.")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();

        if (userService.findById(user.getId()) != null) {
            log.error("이미 가입된 아이디 요청: {}", user.getId());

            response.put("result", "N");
            response.put("error", "이미 가입된 아이디가 있습니다.");
        } else if (userService.findByEmail(user.getEmail()) != null) {
            log.error("이미 가입된 이메일 요청: {}", user.getEmail());

            response.put("result", "N");
            response.put("error", "이미 가입된 이메일이 있습니다.");
        } else {
            // 사용자 등록
            log.info("회원가입 성공: ID = {}", user.getId());
            userService.registerUser(user);

            response.put("result", "Y");
            response.put("error", "");
        }

        Map<String, String> data = new HashMap<>();
        data.put("id", user.getId());
        data.put("companyType", user.getCompanyType());
        data.put("companyName", user.getCompanyName());
        data.put("companyRegNumber", user.getCompanyRegNumber());
        data.put("phoneNumber", user.getPhoneNumber());

        data.put("email", user.getEmail());

        response.put("data", data);

        return ResponseEntity.ok(response);
    }


    @Operation(summary = "로그인 API", description = "DB에 저장된 회원정보를 이용하여 로그인합니다.")
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        // 요청 본문 출력
        log.info("Received login request with data: {}", loginRequest);
        Map<String, Object> response = new HashMap<>();

        try {
            // 사용자 정보 조회


            User user = userService.findByIdAndActive(loginRequest.getId());
            if (user == null) {
                response.put("result", "N");
                response.put("error", "존재하지 않거나 비활성화 된 계정입니다.");

                Map<String, String> data = new HashMap<>();
                data.put("id", loginRequest.getId());
                response.put("data", data);

                // 여기서는 200 OK를 반환하며, result 값으로 실패를 나타냅니다.
                return ResponseEntity.ok(response);
            }

            // 인증 시도
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtil.createJwt(user.getId(), user.getRole(), 3600000L); // 1시간 유효한 토큰 생성


            //String jwt = "S29oZ3lMcHpBbnRnVW1KbXhZNHJlNzFPdk1KTjdTdXZaZmpySzZhS3Z3RHB6WFlvMFFqY3lCd2RhQ1hwYW5MY0t4QkN4L1lvZFlr";
            // 인증 성공 시 반환할 데이터 구성
            response.put("result", "Y");
            response.put("token", jwt); // JWT 토큰을 응답에 포함

            Map<String, String> data = new HashMap<>();
            data.put("id", user.getId());
            data.put("companyType", user.getCompanyType());
            data.put("companyName", user.getCompanyName());
            data.put("companyRegNumber", user.getCompanyRegNumber());
            data.put("phoneNumber", user.getPhoneNumber());
            data.put("email", user.getEmail());

            response.put("data", data);
            response.put("error", "");

            System.out.println("login Request Parameters: " + response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("result", "N");
            response.put("error", "로그인 실패: " + e.getMessage());

            Map<String, String> data = new HashMap<>();
            data.put("id", loginRequest.getId());
            response.put("data", data);

            // 여기서도 200 OK를 반환하며, result 값으로 실패를 나타냅니다.
            return ResponseEntity.ok(response);
        }
    }

    @Operation(summary = "이메일, 대표연락처 변경사항 저장", description = "이메일, 대표연락처 변경사항을 저장한다.")
    @PostMapping("/update-info")
    public ResponseEntity<?> updateUserInfo(@RequestBody Map<String, String> userInfo) {
        String userId = userInfo.get("userId");
        String email = userInfo.get("email");
        String phoneNumber = userInfo.get("phoneNumber");

        if (userId == null || email == null || phoneNumber == null) {
            return ResponseEntity.badRequest().body("필수 정보가 누락되었습니다.");
        }

        // 사용자 정보 업데이트
        boolean isUpdated = userService.updateUserInfo(userId, email, phoneNumber);

        if (isUpdated) {
            return ResponseEntity.ok(Map.of("result", "Y"));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("result", "error", "message", "업데이트 실패"));
        }
    }






    @Operation(summary = "대표연락처 중복검사", description = "대표연락처 변경 전 데이터베이스에서 중복데이터가 있는지 검사한다.")
    @GetMapping("/check-phoneNumber")
    @ResponseBody
    public ResponseEntity<?> checkPhoneNumber(@RequestParam String phoneNumber) {
        Map<String, Object> response = new HashMap<>();

        if (userService.findByPhoneNumber(phoneNumber) != null) {
            log.error("이미 가입된 대표연락처가 있습니다: {}", phoneNumber);

            response.put("result", "Y");
            response.put("error", "");
        } else {
            // 사용자 등록
            log.info("가입된 대표연락처가 없습니다.: ID = {}", phoneNumber);
            // Assuming user creation logic would be in POST request, removing registerUser call here
            response.put("result", "N");
            response.put("error", "가입된 대표연락처가 없습니다.");
        }

        return ResponseEntity.ok(response);
    }


    @Operation(summary = "비밀번호 찾기", description = "Email을 통해 비밀번호를 찾습니다.")
    @PostMapping("/find-password")
    @ResponseBody
    public ResponseEntity<?> findPassword(@Valid @RequestBody PasswordResetRequest request, BindingResult bindingResult) {

        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            response.put("result", "N");
            response.put("error", errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        String email = request.getEmail();

        User user = userService.findByEmail(email);

        if (user == null) {
            response.put("result", "N");
            response.put("error", "해당 이메일로 등록된 사용자가 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // 임시 비밀번호 생성
        String tempPassword = generateTemporaryPassword();

        // 임시 비밀번호로 비밀번호 업데이트
        userService.updatePassword(user, tempPassword);

        // 이메일 발송
        String message = "Nipa 의약품 이력관리 시스템 계정 비밀번호입니다.: " + tempPassword;
        emailService.sendResetPasswordEmail(user.getEmail(), message);
        System.out.println(message);

        response.put("result", "Y");
        response.put("error", "");
        return ResponseEntity.ok(response);
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

                    .body(Collections.singletonMap("result", "사용자 못찾음 N")); // 사용자를 찾을 수 없음
        }

        // 비밀번호 검증
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("result", "비밀번호 불일치 N")); // 비밀번호가 일치하지 않음
        }

        // 사용자 비활성화 (회원 탈퇴 처리)
        userService.deactivateUser(user);
        return ResponseEntity.ok(Collections.singletonMap("result", "Y")); // 탈퇴 성공
    }


    @Operation(summary = "회원 검증 get요청", description = "이미 가입된 아이디가 있는지 체크한다.")
    @GetMapping("/check-id")
    @ResponseBody
    public ResponseEntity<?> checkUser(@RequestParam String id) {
        Map<String, Object> response = new HashMap<>();

        if (userService.findById(id) != null) {
            log.error("이미 가입된 아이디가 있습니다: {}", id);

            response.put("result", "Y");
            response.put("error", "");
        } else {
            // 사용자 등록
            log.info("가입된 아이디가 없습니다.: ID = {}", id);
            // Assuming user creation logic would be in POST request, removing registerUser call here
            response.put("result", "N");
            response.put("error", "가입된 아이디가 없습니다.");
        }

        return ResponseEntity.ok(response);
    }


    @Operation(summary = "이메일 검증 get요청", description = "이미 가입된 이메일이 있는지 체크한다.")
    @GetMapping("/check-email")
    @ResponseBody
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        Map<String, Object> response = new HashMap<>();

        //email 로 가입된 id 칼럼 값을 가져와서 API 응답 파라미터에 표현 해 주는 로직 필요
        User user = userService.findByEmail(email);
        if (userService.findByEmail(email) != null) {
            log.error("이미 가입된 계정이 있습니다: {}", email);

            response.put("id", user.getId());
            response.put("result", "Y");
            response.put("error", "");
        } else {
            // 사용자 등록
            log.info("가입된 이메일이 없습니다.: ID = {}", email);
            // Assuming user creation logic would be in POST request, removing registerUser call here
            response.put("result", "N");
            response.put("error", "가입된 이메일이 없습니다.");
        }

        return ResponseEntity.ok(response);
    }


}
