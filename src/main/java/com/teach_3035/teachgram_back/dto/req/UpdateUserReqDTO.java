package com.teach_3035.teachgram_back.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserReqDTO(
        @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
        String name,

        @Size(min = 5, max = 70, message = "Email must be between 5 and 70 characters")
        @Email(message = "Invalid email format")
        String email,

        @Size(min = 3, max = 100, message = "Username must be between 3 and 100 characters")
        String username,

        @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
        String description,

        @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
        String phoneNumber,

        @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
        String password,

        @Size(min = 5, max = 255, message = "Profile picture URL must be between 5 and 255 characters")
        String profilePictureUrl
) {
}
