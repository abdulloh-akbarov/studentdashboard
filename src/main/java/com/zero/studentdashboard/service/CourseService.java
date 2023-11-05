package com.zero.studentdashboard.service;

import com.zero.studentdashboard.dto.CourseDto;
import com.zero.studentdashboard.model.Response;

public interface CourseService{
    Response getAllByUniversity(Long universityId);
    Response save(CourseDto courseDto);
}
