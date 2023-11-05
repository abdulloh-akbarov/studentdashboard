package com.zero.studentdashboard.model;

import com.zero.studentdashboard.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * CustomUserDetails class represents a custom implementation of the Spring Security UserDetails interface.
 * It is used to store user details and authorities for authentication and authorization purposes.
 *
 * @author Abdulloh Akbarov
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

    private String username;
    private GrantedAuthority authority;

    /**
     * Constructor for CustomUserDetails class.
     *
     * @param username  The username of the user.
     * @param userRole  The user's role, which will be used to create the user's authority.
     */
    public CustomUserDetails(String username, UserRole userRole) {
        this.username = username;
        this.authority = new SimpleGrantedAuthority(userRole.name());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return null;
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
