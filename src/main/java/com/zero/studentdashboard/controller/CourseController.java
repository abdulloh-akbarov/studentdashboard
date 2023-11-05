package com.zero.studentdashboard.controller;

import com.zero.studentdashboard.dto.CourseDto;
import com.zero.studentdashboard.model.Response;
import com.zero.studentdashboard.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {
    private final CourseService service;

    /**
     * Retrieves all courses associated with a specific university.
     *
     * @param id The ID of the university for which courses need to be retrieved.
     * @return A Response object containing the list of courses for the university or an error message.
     */
    @GetMapping("/all/{id}")
    Response getAllByUniversityId(@PathVariable Long id){
        log.info(">> getAllByUniversityId:");
        return service.getAllByUniversity(id);
    }

    /**
     * Adds a new course based on the provided course details.
     *
     * @param courseDto The course's details, including the course name and the university to which it belongs.
     * @return A Response object indicating the outcome of the addition process.
     * @see CourseDto
     */
    @PostMapping("/add")
    Response saveCountry(@Valid @RequestBody CourseDto courseDto){
        log.info(">> saveCountry:");
        return service.save(courseDto);
    }
}
