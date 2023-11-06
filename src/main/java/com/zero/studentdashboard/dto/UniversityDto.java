package com.zero.studentdashboard.dto;

import com.zero.studentdashboard.domain.University;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for University.
 * <p>
 * This record is used to create dto for University.
 *
 * @param id University id.
 * @param universityName Unique University name.
 */
public record UniversityDto(
        Long id,
        @NotBlank(message = "countryId cannot be blank.")
        Long countryId,
        @NotBlank(message = "Name cannot be blank.")
        String universityName) {
        public University toEntity(){
                return new University(id, universityName, null, null);
        }
}
