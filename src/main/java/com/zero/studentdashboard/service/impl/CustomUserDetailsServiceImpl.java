/**
 * The `CustomUserDetailsServiceImpl` class is an implementation of the Spring Security UserDetailsService interface.
 * It is responsible for loading user details from the repository and converting them into CustomUserDetails objects
 * for authentication and authorization.
 *
 * @author Abdulloh Akbarov
 */
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
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Load user details by username from the repository and create a CustomUserDetails object.
     *
     * @param username The username of the user to be loaded.
     * @return A UserDetails object representing the user's details.
     * @throws UsernameNotFoundException If the user is not found in the repository.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(
                user.getUsername(),
                user.getUserRole()
        );

        return customUserDetails;
    }
}
