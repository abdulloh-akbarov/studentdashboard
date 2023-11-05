package com.zero.studentdashboard.service;

import com.zero.studentdashboard.dto.CountryDto;
import com.zero.studentdashboard.model.Response;
import jakarta.transaction.Transactional;

public interface CountryService{
    Response getAll();

    @Transactional
    Response save(CountryDto countryDto);
}
