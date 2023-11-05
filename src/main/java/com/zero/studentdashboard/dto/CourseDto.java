package com.zero.studentdashboard.dto;

import com.zero.studentdashboard.domain.Course;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for Course.
 * <p>
 * This record is used to create dto for Course.
 *
 * @param id Course id.
 * @param name Unique course name.
 */
public record CourseDto(
        Long id,
        @NotBlank(message = "UniversityId cannot be blank.")
        Long universityId,

        @NotBlank(message = "Name cannot be blank.")
        String name) {

        /**
         * Converts the DTO to an entity representation.
         *
         * @return A Cource entity representing the application information.
         */
        public Course toEntity(){
                return new Course(id, name, null);
        }
}
