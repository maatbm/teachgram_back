package com.teach_3035.teachgram_back.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserReqDTO(
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
        String name,

        @NotBlank(message = "Email cannot be blank")
        @Size(min = 5, max = 150, message = "Email must be between 5 and 150 characters")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Username cannot be blank")
        @Size(min = 3, max = 100, message = "Username must be between 3 and 100 characters")
        String username,

        @NotBlank(message = "Description cannot be blank")
        @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
        String description,

        @NotBlank(message = "Phone number cannot be blank")
        @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
        String phoneNumber,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
        String password,

        @NotBlank(message = "Profile picture URL cannot be blank")
        @Size(min = 5, max = 255, message = "Profile picture URL must be between 5 and 255 characters")
        String profilePictureUrl
) {
}
