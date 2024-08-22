package com.example.drugtrack.security.entity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {
    private Long seq;
    private String id;
    private String password;
    private String role;
    private String username;

    public UserPrincipal(Long seq, String id, String password, String role, String username) {
        this.seq = seq;
        this.id = id;
        this.password = password;
        this.role = role;
        this.username = username;
    }

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
        return Collections.singletonList(new SimpleGrantedAuthority(role));
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
}
