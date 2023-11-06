package com.zero.studentdashboard.dto;

import com.zero.studentdashboard.domain.Country;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for Country.
 * <p>
 * This record is used to create dto for Country.
 *
 * @param id Course id.
 * @param countryName Unique country name.
 */
public record CountryDto(
        Long id,
        @NotBlank(message = "Country name cannot be blank.")
        String countryName) {

        /**
         * Converts the DTO to an entity representation.
         *
         * @return A Country entity representing the application information.
         */
        public Country toEntity(){
                return new Country(id, countryName, null);
        }
}
