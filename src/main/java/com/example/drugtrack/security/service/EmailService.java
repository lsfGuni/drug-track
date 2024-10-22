package com.example.drugtrack.security.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
/**
 * EmailService 클래스는 비밀번호 재설정 이메일을 전송하는 서비스를 제공합니다.
 */
@Service
public class EmailService {

    private final JavaMailSender mailSender;
    /**
     * JavaMailSender를 주입받아 EmailService를 초기화합니다.
     *
     * @param mailSender 이메일 전송을 위한 JavaMailSender
     */
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * 비밀번호 재설정 이메일을 전송하는 메서드입니다.
     *
     * @param toEmail 이메일 수신자 주소
     * @param body    이메일 본문 내용 (비밀번호 재설정 관련 정보 포함)
     */
    public void sendResetPasswordEmail(String toEmail, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail); // 수신자 이메일 주소 설정
        message.setSubject("Password Reset Request");  // 이메일 제목 설정
        message.setText(body); // 이메일 본문 설정

        try {
            // 이메일 전송
            mailSender.send(message);
            System.out.println("Email sent successfully to " + toEmail);
        } catch (Exception e) {
            // 이메일 전송 실패 시 예외 처리
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }
}
