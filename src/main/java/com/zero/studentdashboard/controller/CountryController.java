package com.zero.studentdashboard.controller;

import com.zero.studentdashboard.dto.CountryDto;
import com.zero.studentdashboard.model.Response;
import com.zero.studentdashboard.service.CountryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/countries")
public class CountryController {
    private final CountryService service;

    /**
     * Retrieves all countries available in the system.
     *
     * @return A Response object containing the list of countries or an error message.
     */
    @GetMapping("/all")
    Response getAll(){
        log.info(">> getAll:");
        return service.getAll();
    }

    /**
     * Adds a new country based on the provided country details.
     *
     * @param countryDto The country's details, including the country name.
     * @return A Response object indicating the outcome of the addition process.
     * @see CountryDto
     */
    @PostMapping("/add")
    Response saveCountry(@Valid @RequestBody CountryDto countryDto){
        log.info(">> saveCountry:");
        return service.save(countryDto);
    }
}
