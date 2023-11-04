package com.zero.studentdashboard.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for University.
 * <p>
 * This record is used to create dto for University.
 *
 * @param id University id.
 * @param name Unique University name.
 */
public record UniversityDto(
        Long id,
        @NotBlank(message = "Name cannot be blank.")
        String name,
        @NotBlank(message = "Location cannot be empty.")
        String country) {
}
