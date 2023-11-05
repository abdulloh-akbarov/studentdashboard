package com.zero.studentdashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

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
}
