package com.zero.studentdashboard.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for Customer Login.
 * <p>
 * This record is used to create dto for user Login credentials.
 *
 * @param loginType User login type email or username.
 * @param password The customer's password.
 */
public record LoginDto(
        String loginType,
        @NotBlank(message = "password cannot be empty.")
        String password) {
}
