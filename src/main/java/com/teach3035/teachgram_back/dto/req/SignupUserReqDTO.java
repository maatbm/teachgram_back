package com.teach3035.teachgram_back.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupUserReqDTO(
        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
        String name,

        @NotBlank(message = "Email is required")
        @Size(min = 5, max = 100, message = "Email must be between 5 and 100 characters")
        @Email(message = "Email invalid format.")
        String mail,

        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 100, message = "Username must be between 3 and 100 characters")
        String username,

        @NotBlank(message = "Description is required")
        @Size(min = 10, max = 550, message = "Description must be between 10 and 550 characters")
        String description,

        @NotBlank(message = "Phone is required")
        @Size(min = 8, max = 20, message = "Phone must be between 8 and 20 characters")
        String phone,

        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
        String password,

        @NotBlank(message = "Profile picture URL is required")
        @Size(min = 5, max = 255, message = "Profile picture URL must be between 5 and 255 characters")
        String profileLink
) {
}
