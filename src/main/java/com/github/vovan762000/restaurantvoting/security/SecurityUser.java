package com.github.vovan762000.restaurantvoting.security;


import com.github.vovan762000.restaurantvoting.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@Getter
public class SecurityUser implements UserDetails {
    private final String username;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean isActive;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
    public static UserDetails fromUser(User user){
        return  new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(),
                user.isEnabled(),
                user.isEnabled(),
                user.isEnabled(),
                user.isEnabled(),
                user.getRole().getAuthorities()
        );
    }
}
