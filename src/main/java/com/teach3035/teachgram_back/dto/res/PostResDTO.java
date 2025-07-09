package com.teach3035.teachgram_back.dto.res;

public record PostResDTO(
        Long id,
        String title,
        String description,
        String photoLink,
        String videoLink,
        Long likes
) {
}
