package com.zero.studentdashboard.dto;

import com.zero.studentdashboard.domain.Country;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for Country.
 * <p>
 * This record is used to create dto for Country.
 *
 * @param id Course id.
 * @param name Unique course name.
 */
public record CountryDto(
        Long id,
        @NotBlank(message = "Name cannot be blank.")
        String name) {
        public Country toEntity(){
                return new Country(id, name, null);
        }
}
