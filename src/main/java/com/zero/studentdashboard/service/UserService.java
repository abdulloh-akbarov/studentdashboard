package com.zero.studentdashboard.service;

import com.zero.studentdashboard.dto.LoginDto;
import com.zero.studentdashboard.dto.UserDto;
import com.zero.studentdashboard.model.Response;

public interface UserService{
    Response register(UserDto userDto);
    Response login(LoginDto loginDto);
}
