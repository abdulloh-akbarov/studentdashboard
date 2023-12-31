package com.zero.studentdashboard.dto;

import com.zero.studentdashboard.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

/**
 * DTO for User.
 * <p>
 * This record is used to create a DTO for User.
 *
 * @param firstName First name of the user.
 * @param lastName  Last name of the user.
 * @param username  Unique username for the user.
 * @param email     Email address of the user.
 * @param password  Password for the user.
 */
public record UserDto(
        Long id,
        @NotBlank(message = "First name cannot be blank.")
        String firstName,
        @NotBlank(message = "Last name cannot be blank.")
        String lastName,
        @NotBlank(message = "Username cannot be blank.")
        String username,

        @Pattern(regexp = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b",
                message = "Incorrect email.")
        String email,
        @NotBlank(message = "Password cannot be blank.")
        @Length(min = 8, max = 32, message = "Password cannot be less than 8 and greater than 32")
        String password) {

        /**
         * Converts the DTO to an entity representation.
         *
         * @return A User entity representing the application information.
         */
        public User toEntity(){
                return new User(id, firstName, lastName, null,username,email, null, null);
        }
}
