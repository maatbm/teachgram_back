package com.teach3035.teachgram_back.dto.res;

import java.util.List;

public record PagePostResDTO(
        List<PostResDTO> posts,
        Long totalItems,
        int totalPages,
        boolean hasmore
) {
}
