package com.teach3035.teachgram_back.dto.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdatePostReqDTO(
        @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
        String title,

        @Size(min = 3, max = 300, message = "Description must be between 3 and 300 characters")
        String description,

        @Size(min = 5, max = 255, message = "Photo link must be between 5 and 255 characters")
        String photoLink,

        @Size(min = 5, max = 255, message = "Video link must be between 5 and 255 characters")
        String videoLink,

        Boolean isPrivate
) {
}
