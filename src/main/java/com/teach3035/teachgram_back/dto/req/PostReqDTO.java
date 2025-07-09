package com.teach3035.teachgram_back.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostReqDTO(
        @NotBlank(message = "Title is required")
        @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
        String title,

        @NotBlank(message = "Description is required")
        @Size(min = 3, max = 300, message = "Description must be between 3 and 300 characters")
        String description,

        @Size(min = 5, max = 255, message = "Photo link must be between 5 and 255 characters")
        String photoLink,

        @Size(min = 5, max = 255, message = "Video link must be between 5 and 255 characters")
        String videoLink
) {
}
