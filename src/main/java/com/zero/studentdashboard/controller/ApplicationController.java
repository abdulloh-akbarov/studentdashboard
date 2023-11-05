package com.zero.studentdashboard.controller;

import com.zero.studentdashboard.dto.ApplicationDto;
import com.zero.studentdashboard.model.Response;
import com.zero.studentdashboard.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {
    private final ApplicationService service;

    /**
     * Retrieves all applications related to the currently authenticated user.
     *
     * @return A Response object containing the list of applications or an error message.
     */
    @GetMapping("/user")
    public Response getAllApplicationsByUser(){
        log.info(">> getAllApplicationsByUser: ");
        return service.getAllByUser();
    }

    /**
     * Retrieves all applications in the system.
     *
     * @return A Response object containing the list of all applications or an error message.
     */
    @GetMapping("/all")
    public Response getAllApplications(){
        log.info(">> getAllApplications: ");
        return service.getAll();
    }

    /**
     * Creates a new application based on the provided application details.
     *
     * @param applicationDto The application's details including first name, last name, email, and course ID.
     * @return A Response object indicating the outcome of the creation process.
     * @see ApplicationDto
     */
    @PostMapping("/create")
    public Response createApplication(@Valid @RequestBody ApplicationDto applicationDto){
        log.info(">> createApplication: " + applicationDto);
        return service.save(applicationDto);
    }
}
