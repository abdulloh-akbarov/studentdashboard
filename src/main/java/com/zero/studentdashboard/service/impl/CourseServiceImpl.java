package com.zero.studentdashboard.service.impl;

import com.zero.studentdashboard.domain.Course;
import com.zero.studentdashboard.domain.University;
import com.zero.studentdashboard.dto.CourseDto;
import com.zero.studentdashboard.model.Response;
import com.zero.studentdashboard.model.enums.Message;
import com.zero.studentdashboard.repository.CourseRepository;
import com.zero.studentdashboard.repository.UniversityRepository;
import com.zero.studentdashboard.service.CourseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for course-related operations, such as getting courses by university and saving courses.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository repository;
    private final UniversityRepository universityRepository;

    /**
     * Retrieves all courses by the given university ID.
     *
     * @param universityId The ID of the university for which courses are to be retrieved.
     * @return A Response object containing the list of courses or an error message.
     */
    @Override
    public Response getAllByUniversity(Long universityId) {
        log.info(">> getAllByUniversity: universityId=" + universityId);

        List<Course> allByUniversityId = repository.findAllByUniversity_Id(universityId);
        if (allByUniversityId.isEmpty()){
            log.warn("<< getAllByUniversity: No courses found for universityId=" + universityId);
            return new Response(Message.COURSE_NOT_FOUND);
        }

        log.info("<< getAllByUniversity: All courses found for universityId=" + universityId);
        return new Response(Message.SUCCESS, allByUniversityId);
    }

    /**
     * Saves a new course based on the provided course details.
     *
     * @param courseDto The course's details including name and university ID.
     * @return A Response object indicating the outcome of the save process.
     * @see CourseDto
     */
    @Override
    @Transactional
    public Response save(CourseDto courseDto) {
        log.info(">> save: " + courseDto);

        University university = universityRepository.findById(courseDto.universityId()).orElse(null);
        if (university == null){
            log.warn("<< save: University not found");
            return new Response(Message.UNIVERSITY_NOT_FOUND);
        }

        Course course = repository.findByNameAndUniversity(courseDto.name(), university).orElse(null);
        if (course != null){
            log.warn("<< save: Course already exists");
            return new Response(Message.COURSE_EXIST);
        }

        try {
            university.getCourses().add(courseDto.toEntity());
            universityRepository.save(university);
            log.info("<< save: Course saved successfully");
            return new Response(Message.SUCCESS);
        } catch (Exception e) {
            log.error("<< save: Exception thrown while saving course: " + e.getMessage());
            return new Response(Message.UNPROCESSABLE);
        }
    }
}
