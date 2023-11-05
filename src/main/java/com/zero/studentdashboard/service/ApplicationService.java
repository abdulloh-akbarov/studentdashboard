package com.zero.studentdashboard.service;

import com.zero.studentdashboard.dto.ApplicationDto;
import com.zero.studentdashboard.model.Response;

public interface ApplicationService{
    Response save(ApplicationDto applicationDto);

    Response getAllByUser();

    Response getAll();
}
