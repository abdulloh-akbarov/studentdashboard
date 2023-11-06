package com.zero.studentdashboard.dto;

import com.zero.studentdashboard.domain.Course;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for Course.
 * <p>
 * This record is used to create dto for Course.
 *
 * @param id Course id.
 * @param courseName Unique course name.
 */
public record CourseDto(
        Long id,
        @NotBlank(message = "UniversityId cannot be blank.")
        Long universityId,

        @NotBlank(message = "Course name cannot be blank.")
        String courseName) {

        /**
         * Converts the DTO to an entity representation.
         *
         * @return A Course entity representing the application information.
         */
        public Course toEntity(){
                return new Course(id, courseName, null);
        }
}
