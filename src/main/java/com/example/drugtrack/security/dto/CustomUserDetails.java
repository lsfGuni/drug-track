package com.example.drugtrack.security.dto;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.example.drugtrack.security.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
/**
 * CustomUserDetails 클래스는 Spring Security의 UserDetails 인터페이스를 구현하여
 * 사용자 정보를 제공하는 역할을 합니다. 이 클래스는 주로 사용자 인증 및 권한 부여
 * 과정에서 사용됩니다.
 */
public class CustomUserDetails implements UserDetails {
    // User 엔티티 객체를 참조하여 사용자 정보를 관리
    private final User user;

    /**
     * CustomUserDetails 생성자. User 객체를 받아서 초기화합니다.
     *
     * @param user User 엔티티 객체
     */
    public CustomUserDetails(User user) {
        this.user = user;
    }

    /**
     * 사용자의 권한 정보를 반환합니다. 이 메서드는 Spring Security가 사용자의 권한을
     * 확인할 때 호출됩니다. 여기서는 사용자의 역할을 GrantedAuthority 형태로 변환하여 반환합니다.
     *
     * @return 사용자의 GrantedAuthority 권한 목록
     */
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList();
        collection.add(new GrantedAuthority() {
            public String getAuthority() {
                return CustomUserDetails.this.user.getRole(); // 사용자의 역할을 권한으로 반환
            }
        });
        return collection;
    }

    public String getPassword() {
        return this.user.getPassword();
    }

    public String getUsername() {
        return this.user.getUsername();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}
