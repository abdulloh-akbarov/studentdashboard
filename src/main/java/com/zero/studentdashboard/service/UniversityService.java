package com.zero.studentdashboard.service;

import com.zero.studentdashboard.dto.UniversityDto;
import com.zero.studentdashboard.model.Response;

public interface UniversityService{
    Response getAllByCountry(Long countryId);
    Response save(UniversityDto universityDto);
}
