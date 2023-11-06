package com.zero.studentdashboard.service.impl;

import com.zero.studentdashboard.domain.User;
import com.zero.studentdashboard.domain.enums.UserRole;
import com.zero.studentdashboard.dto.LoginDto;
import com.zero.studentdashboard.dto.UserDto;
import com.zero.studentdashboard.model.CustomUserDetails;
import com.zero.studentdashboard.model.Response;
import com.zero.studentdashboard.model.enums.Message;
import com.zero.studentdashboard.repository.UserRepository;
import com.zero.studentdashboard.service.UserService;
import com.zero.studentdashboard.util.JwtTokenUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtTokenUtil util;

    /**
     * Registers a new user based on the provided user details.
     *
     * @param userDto The user's details including username, email, and password.
     * @return A Response object indicating the outcome of the registration process.
     * @see UserDto
     */
    @Override
    @Transactional
    public Response register(UserDto userDto) {
        log.info(">> register: username={}, email={}", userDto.username(), userDto.email());

        boolean existsByUsername = repository.existsByUsername(userDto.username());
        if (existsByUsername){
            log.warn("<< register: Username exist: " + userDto.username());
            return new Response(Message.USERNAME_EXIST);
        }

        boolean existsByEmail = repository.existsByEmail(userDto.email());
        if (existsByEmail){
            log.warn("<< register: Email exist: " + userDto.email());
            return new Response(Message.EMAIL_EXIST);
        }
        try {
            User entity = userDto.toEntity();
            entity.setUserRole(UserRole.STUDENT);
            entity.setPassword(encoder.encode(userDto.password()));
            repository.save(entity);
            Map<String, String> token = getToken(entity);

            log.info("<< register: User Created Successfully ");
            return new Response(Message.SUCCESS, token);
        } catch (Exception e){
            log.error("<< save: Exception thrown while saving message: " + e.getMessage());
            return new Response(Message.UNPROCESSABLE);
        }
    }

    /**
     * Logs in a user based on the provided login information.
     *
     * @param loginDto The login details, which can include either the username or email and the password.
     * @return A Response object containing a JWT token upon successful login or an error message.
     * @see LoginDto
     */
    @Override
    public Response login(LoginDto loginDto) {
        log.info(">> login: loginType=" + loginDto.loginType());

        String loginType = loginDto.loginType();
        User user;
        if (loginType.contains("@")){
            user = repository.findByEmail(loginType).orElse(null);
        } else{
            user = repository.findByUsername(loginType).orElse(null);
        }

        if (user == null){
            log.warn("<< login: User not found loginType= " + loginDto.loginType());
            return new Response(Message.USER_NOT_FOUND);
        }

        if (user.getUserRole().equals(UserRole.ADMIN)){
            log.warn("<< adminLogin: STUDENT role required");
            return new Response(Message.AUTHENTICATION_FAILED);
        }

        if (encoder.matches(loginDto.password(), user.getPassword())){
            Map<String, String> token = getToken(user);
            log.info("<< login: Successfully logged in");
            return new Response(Message.SUCCESS, token);
        }

        log.warn("<< login: Authentication failed");
        return new Response(Message.AUTHENTICATION_FAILED);
    }
    /**
     * Logs in a user based on the provided login information for admin.
     *
     * @param loginDto The login details, which can include either the username or email and the password.
     * @return A Response object containing a JWT token upon successful login or an error message.
     * @see LoginDto
     */
    @Override
    public Response adminlLogin(LoginDto loginDto) {
        log.info(">> adminLogin: loginType=" + loginDto.loginType());

        String loginType = loginDto.loginType();
        User user;
        if (loginType.contains("@")){
            user = repository.findByEmail(loginType).orElse(null);
        } else{
            user = repository.findByUsername(loginType).orElse(null);
        }

        if (user == null){
            log.warn("<< adminLogin: User not found loginType= " + loginDto.loginType());
            return new Response(Message.USER_NOT_FOUND);
        }

        if (user.getUserRole().equals(UserRole.STUDENT)){
            log.warn("<< adminLogin: ADMIN role required");
            return new Response(Message.AUTHENTICATION_FAILED);
        }

        if (encoder.matches(loginDto.password(), user.getPassword())){
            Map<String, String> token = getToken(user);
            log.info("<< adminLogin: Successfully logged in");
            return new Response(Message.SUCCESS, token);
        }

        log.warn("<< adminLogin: Authentication failed");
        return new Response(Message.AUTHENTICATION_FAILED);
    }

    /**
     * Generates a JWT token for a user after successful login or registration.
     *
     * @param user The user for whom the token is generated.
     * @return A Map containing the generated JWT token with the key "ACCESS_TOKEN."
     * @see CustomUserDetails
     */
    private Map<String, String> getToken(User user){
        CustomUserDetails customUserDetails = new CustomUserDetails(
                user.getUsername(),
                user.getUserRole());
        String token = util.generateToken(customUserDetails);
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put("ACCESS_TOKEN", token);
        return tokens;
    }
}
