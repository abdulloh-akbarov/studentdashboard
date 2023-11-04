package com.zero.studentdashboard.dto;

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
        @NotBlank(message = "Name cannot be blank.")
        String name) {
}
