package com.zero.studentdashboard.controller;

import com.zero.studentdashboard.dto.LoginDto;
import com.zero.studentdashboard.model.Response;
import com.zero.studentdashboard.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService service;

    /**
     * Handles the POST request for admin login.
     *
     * @param loginDto The LoginDto object containing user login details.
     * @return A Response object indicating the outcome of the login operation.
     * @see LoginDto
     */
    @PostMapping("/login")
    public Response login(@Valid @RequestBody LoginDto loginDto){
        log.info(">> login: loginType=" + loginDto.loginType());
        return service.adminlLogin(loginDto);
    }
}
