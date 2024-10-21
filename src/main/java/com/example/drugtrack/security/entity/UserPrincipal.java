package com.example.drugtrack.security.entity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
/**
 * UserPrincipal 클래스는 Spring Security의 UserDetails 인터페이스를 구현하여
 * 인증된 사용자 정보를 제공하는 엔티티 클래스입니다.
 * 이 클래스는 주로 Spring Security의 인증 메커니즘에서 사용자 정보를 다루는 데 사용됩니다.
 */
public class UserPrincipal implements UserDetails {
    private Long seq; //사용자 고유 식별자 (seq).
    private String id; // 사용자 ID.
    private String password; // 사용자 비밀번호.
    private String role; // 사용자 역할 (role). 예: "ROLE_USER", "ROLE_ADMIN".
    private String username; // 사용자 이름 (username).


    /**
     * UserPrincipal 생성자.
     * 사용자 식별자, ID, 비밀번호, 역할, 이름을 설정합니다.
     *
     * @param seq       사용자 식별자
     * @param id        사용자 ID
     * @param password  사용자 비밀번호
     * @param role      사용자 역할
     * @param username  사용자 이름
     */
    public UserPrincipal(Long seq, String id, String password, String role, String username) {
        this.seq = seq;
        this.id = id;
        this.password = password;
        this.role = role;
        this.username = username;
    }

    /**
     * 주어진 User 엔티티로부터 UserPrincipal 객체를 생성합니다.
     *
     * @param user User 엔티티
     * @return UserPrincipal 객체
     */
    public static UserPrincipal create(User user) {
        return new UserPrincipal(
                user.getSeq(),
                user.getId(),
                user.getPassword(),
                user.getRole(),
                user.getUsername()
        );
    }

    public Long getSeq() {
        return seq;
    }

    public String getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }
}
