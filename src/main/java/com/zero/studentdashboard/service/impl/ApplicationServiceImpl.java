package com.zero.studentdashboard.service.impl;

import com.zero.studentdashboard.domain.Application;
import com.zero.studentdashboard.domain.Course;
import com.zero.studentdashboard.domain.User;
import com.zero.studentdashboard.dto.ApplicationDto;
import com.zero.studentdashboard.model.Response;
import com.zero.studentdashboard.model.enums.Message;
import com.zero.studentdashboard.repository.ApplicationRepository;
import com.zero.studentdashboard.repository.CourseRepository;
import com.zero.studentdashboard.repository.UserRepository;
import com.zero.studentdashboard.service.ApplicationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository repository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    /**
     * Retrieves all applications related to the currently logged-in user.
     *
     * @return A Response object containing a list of applications related to the user.
     */
    @Override
    public Response getAllByUser() {
        log.info(">> getAllByUser:");

        List<Application> allByUser = repository.findAllByUser(exctractUser());
        if (allByUser.isEmpty()){
            log.warn("<< getAllByUser: No Application found");
            return new Response(Message.APPLICATION_NOT_FOUND);
        }

        log.info("<< getAllByUser: All Applications related to the user retrieved");
        return new Response(Message.SUCCESS, allByUser);
    }

    /**
     * Retrieves all applications.
     *
     * @return A Response object containing a list of all applications.
     */
    @Override
    public Response getAll() {
        log.info(">> getAll:");

        List<Application> all = repository.findAll();
        if (all.isEmpty()){
            log.warn("<< getAll: No Application found");
            return new Response(Message.APPLICATION_NOT_FOUND);
        }

        log.info("<< getAll: All Applications related to the user retrieved");
        return new Response(Message.SUCCESS, all);
    }

    /**
     * Saves an application.
     *
     * @param applicationDto The DTO containing application details.
     * @return A Response object indicating the outcome of the save operation.
     * @see ApplicationDto
     */
    @Override
    @Transactional
    public Response save(ApplicationDto applicationDto) {
        log.info(">> save: " + applicationDto);

        Course course = courseRepository.findById(applicationDto.id()).orElse(null);
        if (course == null){
            log.warn("<< save: Course not found by id=" + applicationDto.courseId());
            return new Response(Message.COURSE_NOT_FOUND);
        }

        User user = exctractUser();
        if (user == null){
            log.warn("<< save: User not found");
            return new Response(Message.USER_NOT_FOUND);
        }

        try {
            Application entity = applicationDto.toEntity();
            entity.setCourse(course);
            entity.setUser(user);
            repository.save(entity);
            log.info("<< save: Application saved successfully");
            return new Response(Message.SUCCESS);
        } catch(Exception e){
            log.error("<< save: Exception thrown while saving message: " + e.getMessage());
            return new Response(Message.UNPROCESSABLE);
        }
    }

    /**
     * This method used to extract UserDetails from the Security context.
     */
    private User exctractUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        return userRepository.findByUsername(username).orElse(null);
    }
}
