package com.zero.studentdashboard.controller;

import com.zero.studentdashboard.dto.UniversityDto;
import com.zero.studentdashboard.model.Response;
import com.zero.studentdashboard.service.UniversityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling university-related operations, such as retrieving universities by country and adding new universities.
 */
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/universities")
public class UniversityController {
    private final UniversityService service;

    /**
     * Retrieves all universities associated with a specific country.
     *
     * @param id The ID of the country for which universities need to be retrieved.
     * @return A Response object containing the list of universities for the country or an error message.
     */
    @GetMapping("/all/{id}")
    Response getAllByCountryId(@PathVariable Long id){
        log.info(">> getAllByUniversityId:");
        return service.getAllByCountry(id);
    }

    /**
     * Adds a new university based on the provided university details.
     *
     * @param universityDto The university's details, including the university name and the country to which it belongs.
     * @return A Response object indicating the outcome of the addition process.
     * @see UniversityDto
     */
    @PostMapping("/add")
    Response saveCountry(@Valid @RequestBody UniversityDto universityDto){
        log.info(">> saveCountry:");
        return service.save(universityDto);
    }
}
