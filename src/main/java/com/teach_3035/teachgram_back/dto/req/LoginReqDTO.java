package com.teach_3035.teachgram_back.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginReqDTO(

        @NotBlank(message = "Email cannot be blank")
        @Size(min = 5, max = 70, message = "Email must be between 5 and 70 characters")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
        String password
) {
}
