package com.teach3035.teachgram_back.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SigninReqDTO(
        @NotBlank(message = "Email is required")
        @Size(min = 5, max = 100, message = "Email must be between 5 and 100 characters")
        @Email(message = "Email invalid format.")
        String mail,

        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
        String password
) {
}
