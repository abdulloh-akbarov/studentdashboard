package com.zero.studentdashboard.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for Customer Login.
 * <p>
 * This record is used to create dto for user Login credentials.
 *
 * @param username User's username;.
 * @param email User's email.
 * @param password The customer's password.
 */
public record LoginDto(
        String username,
        String email,
        @NotBlank(message = "password cannot be empty.")
        String password) {
}
