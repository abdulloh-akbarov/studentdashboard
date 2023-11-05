package com.zero.studentdashboard.dto;

import com.zero.studentdashboard.domain.Application;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * DTO for Application.
 * <p>
 * This record is used to create a DTO for Application.
 *
 * @param id        Application id.
 * @param firstName First name of the applicant.
 * @param lastName  Last name of the applicant.
 * @param email     Email of the applicant.
 * @param courseId  ID of the associated Course.
 */
public record ApplicationDto(
        Long id,
        @NotBlank(message = "First name cannot be blank.")
        String firstName,
        @NotBlank(message = "Last name cannot be blank.")
        String lastName,
        @Pattern(regexp = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b",
                message = "Incorrect email.")
        String email,
        @NotBlank(message = "Course id cannot be null.")
        Long courseId) {

        /**
         * Converts the DTO to an entity representation.
         *
         * @return An Application entity representing the application information.
         */
        public Application toEntity(){
              return new Application(id, firstName, lastName, email, null, null);
        }
}
