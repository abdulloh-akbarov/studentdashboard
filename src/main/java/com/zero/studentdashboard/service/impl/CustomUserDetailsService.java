package com.zero.studentdashboard.service.impl;

import com.zero.studentdashboard.domain.User;
import com.zero.studentdashboard.model.CustomUserDetails;
import com.zero.studentdashboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(
            user.getUsername(),
            user.getPassword(),
            user.getUserRole()
        );

        return customUserDetails;
    }
}
