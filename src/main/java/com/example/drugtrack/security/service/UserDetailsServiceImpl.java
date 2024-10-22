package com.example.drugtrack.security.service;

import com.example.drugtrack.security.entity.User;
import com.example.drugtrack.security.entity.UserPrincipal;
import com.example.drugtrack.security.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * UserDetailsServiceImpl 클래스는 Spring Security의 UserDetailsService를 구현하여
 * 사용자 인증을 처리하는 서비스입니다.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * UserRepository를 주입받아 UserDetailsServiceImpl을 초기화합니다.
     *
     * @param userRepository 사용자 정보를 관리하는 리포지토리
     */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 사용자 ID를 기반으로 사용자 정보를 로드하여 UserDetails를 반환합니다.
     *
     * @param id 사용자 ID
     * @return 사용자 정보를 담은 UserDetails 객체
     * @throws UsernameNotFoundException 사용자 ID로 사용자를 찾지 못한 경우 예외를 발생
     */
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        // 사용자 ID로 데이터베이스에서 사용자를 찾습니다.
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + id));

        // UserPrincipal 객체를 생성하여 반환
        return UserPrincipal.create(user);
    }
}
