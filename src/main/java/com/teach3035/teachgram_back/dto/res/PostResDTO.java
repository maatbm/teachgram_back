package com.teach3035.teachgram_back.dto.res;

import java.time.LocalDateTime;

public record PostResDTO(
        Long id,
        String title,
        String description,
        String photoLink,
        String videoLink,
        Boolean isPrivate,
        UserResDTO user,
        Long likes,
        LocalDateTime createdAt
) {
}
